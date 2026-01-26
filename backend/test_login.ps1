$body = @{
    email    = "uniqueuser1993320369@example.com"
    password = "password123"
} | ConvertTo-Json

Write-Host "Testing Login Endpoint..."
Write-Host "Request Body: $body"

try {
    $response = Invoke-RestMethod -Uri 'http://localhost:8080/api/auth/authenticate' -Method Post -Body $body -ContentType 'application/json'
    Write-Host "SUCCESS!"
    Write-Host "Response: $($response | ConvertTo-Json)"
}
catch {
    Write-Host "FAILED!"
    Write-Host "Status Code: $($_.Exception.Response.StatusCode.value__)"
    Write-Host "Error Message: $($_.ErrorDetails.Message)"
    Write-Host "Full Exception: $($_.Exception.Message)"
}
