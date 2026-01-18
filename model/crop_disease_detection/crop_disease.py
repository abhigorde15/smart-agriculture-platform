from flask import Flask, request, jsonify
from flask_cors import CORS
import tensorflow as tf
import numpy as np
from PIL import Image
import os

from huggingface_hub import hf_hub_download

app = Flask(__name__)
CORS(app)

HF_REPO_ID = "abhigorde15/crop-disease-detection"
HF_MODEL_FILE = "crop_disease_detection.keras"

print("Downloading model from Hugging Face...")
MODEL_PATH = hf_hub_download(
    repo_id=HF_REPO_ID,
    filename=HF_MODEL_FILE
)

print("Loading model...")
model = tf.keras.models.load_model(MODEL_PATH)
print("Model loaded successfully!")

IMG_SIZE = (160, 160)

CLASS_NAMES = [
    'Apple___Apple_scab', 'Apple___Black_rot', 'Apple___Cedar_apple_rust',
    'Apple___healthy', 'Background_without_leaves', 'Blueberry___healthy',
    'Cherry___Powdery_mildew', 'Cherry___healthy',
    'Corn___Cercospora_leaf_spot Gray_leaf_spot', 'Corn___Common_rust',
    'Corn___Northern_Leaf_Blight', 'Corn___healthy',
    'Grape___Black_rot', 'Grape___Esca_(Black_Measles)',
    'Grape___Leaf_blight_(Isariopsis_Leaf_Spot)', 'Grape___healthy',
    'Orange___Haunglongbing_(Citrus_greening)',
    'Peach___Bacterial_spot', 'Peach___healthy',
    'Pepper,_bell___Bacterial_spot', 'Pepper,_bell___healthy',
    'Potato___Early_blight', 'Potato___Late_blight', 'Potato___healthy',
    'Raspberry___healthy', 'Soybean___healthy',
    'Squash___Powdery_mildew',
    'Strawberry___Leaf_scorch', 'Strawberry___healthy',
    'Tomato___Bacterial_spot', 'Tomato___Early_blight',
    'Tomato___Late_blight', 'Tomato___Leaf_Mold',
    'Tomato___Septoria_leaf_spot',
    'Tomato___Spider_mites Two-spotted_spider_mite',
    'Tomato___Target_Spot',
    'Tomato___Tomato_Yellow_Leaf_Curl_Virus',
    'Tomato___Tomato_mosaic_virus',
    'Tomato___healthy'
]

@app.route("/api/health", methods=["GET"])
def health():
    return jsonify({"status": "Crop Disease Detection API is running"}), 200


@app.route("/api/predict", methods=["POST"])
def predict():
    try:
        if "image" not in request.files:
            return jsonify({"error": "Image file is required"}), 400

        file = request.files["image"]

        # Preprocess image
        image = Image.open(file).convert("RGB")
        image = image.resize(IMG_SIZE)
        image = np.array(image, dtype=np.float32)

        image = tf.keras.applications.efficientnet.preprocess_input(image)
        image = np.expand_dims(image, axis=0)

        # Prediction
        predictions = model.predict(image)
        predicted_index = int(np.argmax(predictions[0]))
        confidence = float(np.max(predictions[0]) * 100)

        return jsonify({
            "predicted_class": CLASS_NAMES[predicted_index],
            "confidence": round(confidence, 2)
        })

    except Exception as e:
        return jsonify({
            "error": "Prediction failed",
            "details": str(e)
        }), 500



if __name__ == "__main__":
    port = int(os.environ.get("PORT", 5000))
    app.run(host="0.0.0.0", port=port)
