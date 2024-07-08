import base64
import sys

from PIL import Image
import io

def base64_to_png(base64_string):
    try:
        # 解码Base64数据
        image_data = base64.b64decode(base64_string)

        # 创建字节数据的内存文件对象
        image_stream = io.BytesIO(image_data)

        # 使用PIL库打开图像内容
        image = Image.open(image_stream)

        # 保存为PNG文件
        image.save("src/main/resources/image_caption/img.png")
    except base64.binascii.Error as e:
        with open("err.txt", "w") as f:
            f.write(f"Base64解码错误: {e}")
    except IOError as e:
        with open("err.txt", "w") as f:
            f.write(f"图像文件处理或写入错误: {e}")
    except Exception as e:
        with open("err.txt", "w") as f:
            f.write(f"未知错误: {e}")

if __name__ == "__main__":
    try:
        # 从文件读取Base64字符串
        with open("src/main/resources/image_caption/img.txt", "r") as f:
            base64_string = f.read().strip()  # 读取内容并去除首尾空白

        # 调用函数处理Base64字符串
        base64_to_png(base64_string)

    except FileNotFoundError:
        print("Error: 'img.txt' file not found.")
        with open("err.txt", "w") as f:
            f.write("Error: 'img.txt' file not found.")
    except Exception as e:
        print(f"An error occurred: {e}")
        with open("err.txt", "w") as f:
            f.write(f"An error occurred: {e}")