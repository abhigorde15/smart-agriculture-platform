from flask import Flask, request, jsonify
from flask_cors import CORS
import numpy as np
from PIL import Image
import os
import platform
import tensorflow as tf
app = Flask(__name__)
CORS(app)

TFLITE_MODEL_PATH = "crop_disease_detection.tflite"

if not os.path.exists(TFLITE_MODEL_PATH):
    raise FileNotFoundError("TFLite model not found!")

# 🔁 AUTO SWITCH
if platform.system() == "Windows":
    print("Running on Windows → using TensorFlow")
   
    interpreter = tf.lite.Interpreter(model_path=TFLITE_MODEL_PATH)
else:
    print("Running on Linux → using tflite-runtime")
    from tflite_runtime.interpreter import Interpreter
    interpreter = Interpreter(model_path=TFLITE_MODEL_PATH)

print("Loading TFLite model...")
interpreter.allocate_tensors()
input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()
print("TFLite model loaded successfully!")

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

@app.route("/api/health")
def health():
    return jsonify({"status": "OK"}), 200

@app.route("/api/predict", methods=["POST"])
def predict():
    if "image" not in request.files:
        return jsonify({"error": "Image file is required"}), 400

    image = Image.open(request.files["image"]).convert("RGB")
    image = image.resize(IMG_SIZE)

    img = np.array(image, dtype=np.float32)
    img = np.expand_dims(img, axis=0)
    img = tf.keras.applications.efficientnet.preprocess_input(img)


    interpreter.set_tensor(input_details[0]["index"], img)
    interpreter.invoke()

    preds = interpreter.get_tensor(output_details[0]["index"])
    idx = int(np.argmax(preds[0]))
    confidence = float(np.max(preds[0]) * 100)

    return jsonify({
        "predicted_class": CLASS_NAMES[idx],
        "confidence": round(confidence, 2)
    })

if __name__ == "__main__":
    port = int(os.environ.get("PORT", 5000))
    app.run(host="0.0.0.0", port=port)
