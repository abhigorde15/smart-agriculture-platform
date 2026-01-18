from flask import Flask, request, jsonify
from flask_cors import CORS
import numpy as np
import joblib
from huggingface_hub import hf_hub_download

app = Flask(__name__)
CORS(app)

MODEL_PATH = hf_hub_download(
    repo_id="abhigorde15/crop-recommendation",
    filename="crop_model.pkl"
)


model = joblib.load(MODEL_PATH)

@app.route("/api/health", methods=["GET"])
def health():
    return jsonify({"status": "Crop API is running"}), 200


@app.route("/api/predict", methods=["POST"])
def predict_crop():
    try:
        data = request.get_json()

        required_fields = [
            "N", "P", "K",
            "temperature",
            "humidity",
            "ph",
            "rainfall"
        ]

        # Validate input
        for field in required_fields:
            if field not in data:
                return jsonify({"error": f"{field} is required"}), 400

        # Prepare input
        input_data = np.array([[ 
            data["N"],
            data["P"],
            data["K"],
            data["temperature"],
            data["humidity"],
            data["ph"],
            data["rainfall"]
        ]])

        # Prediction
        prediction = model.predict(input_data)

        return jsonify({
            "recommended_crop": prediction[0]
        }), 200

    except Exception as e:
        return jsonify({
            "error": "Prediction failed",
            "details": str(e)
        }), 500


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
