import numpy as np
from PIL import Image
import pylab
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.applications.xception import Xception
from keras.layers import concatenate
from tensorflow.keras.models import load_model
from itertools import chain
from pycocotools.coco import COCO  # COCO python library
import string

pylab.rcParams["figure.figsize"] = (8.0, 10.0)


def extract_features(filename, model):
    try:
        image = Image.open(filename)
    except:
        print(
            "ERROR: Couldn't open image! Make sure the image path and extension is correct"
        )
        return None
    image = image.resize((299, 299))
    image = np.array(image)

    if image.shape[2] == 4:
        image = image[..., :3]
    image = np.expand_dims(image, axis=0)
    image = image / 127.5
    image = image - 1.0
    feature = model.predict(image)
    return feature


def word_for_id(integer, tokenizer):
    for word, index in tokenizer.word_index.items():
        if index == integer:
            return word
    return None


def generate_desc(model, tokenizer, photo, max_length):
    in_text = "start"
    for i in range(max_length):
        sequence = tokenizer.texts_to_sequences([in_text])[0]
        sequence = pad_sequences([sequence], maxlen=max_length)
        pred = model.predict([photo, sequence], verbose=0)
        pred = np.argmax(pred)
        word = word_for_id(pred, tokenizer)

        if word is None:
            break
        in_text += " " + word

        if word == "end":
            break
    return in_text


class image_Cap:
    def __init__(self):
        coco_caps = COCO("./annotations/captions_train2017.json")
        coco = COCO("./annotations/instances_train2017.json")
        dataset = dict()
        imgcaptions = []
        subcategories_imageIds = dict()
        cats = coco.loadCats(coco.getCatIds())
        subcategories = [cat["name"] for cat in cats]
        catIds = coco.getCatIds(catNms=subcategories)
        subcategories_Ids = dict()
        for i in range(0, len(subcategories)):
            subcategories_Ids[subcategories[i]] = catIds[i]
        for i in range(0, len(catIds)):
            imgIds = coco.getImgIds(catIds=catIds[i])
            img = []
            for j in imgIds:
                img.append(j)
            subcategories_imageIds[subcategories[i]] = img
        train_cats = (
            subcategories_imageIds["bicycle"] + subcategories_imageIds["airplane"]
        )
        imgIdss = coco.getImgIds(imgIds=train_cats)
        for imgid in imgIdss:
            img = coco.loadImgs(imgid)[0]
            annIds = coco_caps.getAnnIds(imgIds=img["id"])
            anns = coco_caps.loadAnns(annIds)
            imgcaptions = []
            for cap in anns:
                # Remove punctuation
                cap = cap["caption"].translate(
                    str.maketrans("", "", string.punctuation)
                )
                # Replace - to blank
                cap = cap.replace("-", " ")
                cap = cap.split()
                cap = [word.lower() for word in cap]
                cap = "<start> " + " ".join(cap) + " <end>"
                imgcaptions.append(cap)
            img1 = img["coco_url"]
            img1 = "./train2018/" + img1[-16:]
            dataset[img1] = imgcaptions

        flatten_list = list(
            chain.from_iterable(dataset.values())
        )  # [[1,3],[4,8]] = [1,3,4,8]
        self.tokenizer = Tokenizer(oov_token="<oov>")
        self.tokenizer.fit_on_texts(flatten_list)
        # total_words = len(self.tokenizer.word_index) + 1
        pylab.rcParams["figure.figsize"] = (8.0, 10.0)
        self.max_length = 46
        self.model = load_model("./models/model_0.h5")
        self.xception_model = Xception(include_top=False, pooling="avg")

    def put_word(self, img_path):
        # img_path = "./1.jpg"
        photo = extract_features(img_path, self.xception_model)
        description = generate_desc(self.model, self.tokenizer, photo, self.max_length)
        print("\nGenerated description:1\n", description)
        return description


if __name__ == "__main__":
    image_cap = image_Cap()
    # 生成描述
    des = image_cap.put_word("./img.jpg")
    with open("./output.txt", "w") as f:
        f.write(des)
