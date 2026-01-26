$baseUrl = "http://localhost:8080/api"

echo "1. Testing Registration..."
$regBody = @{
    fullName = "Test Farmer"
    email    = "test@farmer.com"
    password = "password123"
    role     = "FARMER"
} | ConvertTo-Json

try {
    $regResponse = Invoke-RestMethod -Uri "$baseUrl/auth/register" -Method Post -Body $regBody -ContentType "application/json"
    echo "Registration Success: $($null -ne $regResponse.token)"
    $token = $regResponse.token
    $userId = $regResponse.id
}
catch {
    echo "Registration Failed or User Exists: $_"
    # Try login if registration fails
}

if (-not $token) {
    echo "2. Testing Authentication..."
    $authBody = @{
        email    = "test@farmer.com"
        password = "password123"
    } | ConvertTo-Json
    try {
        $authResponse = Invoke-RestMethod -Uri "$baseUrl/auth/authenticate" -Method Post -Body $authBody -ContentType "application/json"
        echo "Authentication Success: $($null -ne $authResponse.token)"
        $token = $authResponse.token
        $userId = $authResponse.id
    }
    catch {
        echo "Authentication Failed: $_"
        exit 1
    }
}

echo "3. Testing Weather Service (With Auth)..."
try {
    $headers = @{ Authorization = "Bearer $token" }
    $weatherResponse = Invoke-RestMethod -Uri "$baseUrl/weather/alerts?location=Delhi" -Method Get -Headers $headers
    echo "Weather Response: $($weatherResponse | ConvertTo-Json -Depth 2)"
}
catch {
    echo "Weather Service Failed: $_"
}

echo "4. Testing Profile Creation..."
$profileBody = @{
    landArea          = 5.5
    location          = "Delhi"
    soilType          = "LOAMY"
    irrigationMethod  = "DRIP"
    state             = "Delhi"
    district          = "New Delhi"
    preferredLanguage = "Hindi"
} | ConvertTo-Json

try {
    $profileResponse = Invoke-RestMethod -Uri "$baseUrl/profiles" -Method Post -Body $profileBody -ContentType "application/json" -Headers $headers
    echo "Profile Created/Updated: $($profileResponse.id)"
}
catch {
    echo "Profile Creation Failed: $_"
}

echo "5. Testing Cloudinary Image Upload (Disease Detection)..."
$imagePath = "C:\Users\Hp\.gemini\antigravity\brain\c19221bb-83ed-4afd-bbc3-8e6852d3e36b\test_leaf_image_1768481693757.png"
if (Test-Path $imagePath) {
    try {
        $boundary = [System.Guid]::NewGuid().ToString()
        $LF = "`r`n"
        $fileBytes = [System.IO.File]::ReadAllBytes($imagePath)
        $fileHeader = "--$boundary$LF" +
        "Content-Disposition: form-data; name=`"file`"; filename=`"test_leaf.png`"$LF" +
        "Content-Type: image/png$LF$LF"
        $fileFooter = "$LF--$boundary--$LF"
        
        $enc = [System.Text.Encoding]::GetEncoding("iso-8859-1")
        $bodyBytes = $enc.GetBytes($fileHeader) + $fileBytes + $enc.GetBytes($fileFooter)

        $uploadUri = "$baseUrl/ai/detect"
        $uploadHeaders = @{ 
            Authorization  = "Bearer $token"; 
            "Content-Type" = "multipart/form-data; boundary=$boundary" 
        }

        # Use Curl or HttpClient because Invoke-RestMethod multipart support is tricky in older PS versions
        # Trying HttpClient method for reliability
        # Actually, let's just stick to a simpler curl if available, or try this:
        $request = [System.Net.HttpWebRequest]::Create($uploadUri)
        $request.Method = "POST"
        $request.ContentType = "multipart/form-data; boundary=$boundary"
        $request.Headers.Add("Authorization", "Bearer $token")
        $stream = $request.GetRequestStream()
        $stream.Write($bodyBytes, 0, $bodyBytes.Length)
        $stream.Close()

        $response = $request.GetResponse()
        $reader = [System.IO.StreamReader]::new($response.GetResponseStream())
        $responseText = $reader.ReadToEnd()
        
        echo "Disease Detection Response: $responseText"
    }
    catch {
        echo "Image Upload Failed: $_"
    }
}
else {
    echo "Test Image not found at $imagePath, skipping upload test."
}
