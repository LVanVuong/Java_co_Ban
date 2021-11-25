"""
Name:Lưu Văn Vương
Class: INT3404 3
MSSV: 18021446


"""

import cv2
import numpy as np
import matplotlib.pyplot as plt
import argparse
import time


def conv_sum(a, b):
    s = (a * b).sum()
    return s

# def conv_sum(a, b):
#     s = 0
#     for i in range(len(a)):
#         for j in range(len(a[0])):
#             s += a[i][j] * b[i][j]
#     return s


def my_convolution(I, g, mode='valid', boundary='zero_padding'):
    h, w = len(g), len(g[0])
    H, W = I.shape[0], I.shape[1]
    bound = int(h / 2)

    if mode == 'valid':
        output_h = H - (h - 1)
        output_w = W - (w - 1)
    else:
        output_h = H
        output_w = W
        I_new = np.zeros([H + 2 * bound, W + 2 * bound], dtype='uint8')
        I_new[bound:-bound, bound:-bound] = I
        I = I_new

    output = [[0 for _ in range(output_w)] for _ in range(output_h)]
    for i in range(output_h):
        for j in range(output_w):
            output[i][j] = conv_sum(I[i:i + h, j:j + w], g)

    return np.array(output)


def init_gaussian_kernel(size=3):
    bound = int(size / 2)

    kernel = [[0 for i in range(size)] for i in range(size)]
    for i in range(size):
        for j in range(size):
            x = np.abs(i - bound)
            y = np.abs(j - bound)
            g = np.exp(-(x ** 2 + y ** 2) / 2)
            kernel[i][j] = g

    sumk = np.array(kernel).sum()
    kernel = [[kernel[i][j]/sumk for i in range(size)] for j in range(size)]
    return kernel


def init_sobel_kernel(x=True):
    sobel_x = [[-1, 0, 1], [-2, 0, 2], [-1, 0, 1]]
    sobel_y = [[1, 2, 1], [0, 0, 0], [-1, -2, -1]]
    return sobel_x if x == True else sobel_y


def sobel_filer(input_file, output_file, size_gkernel=3):
    img = cv2.imread(input_file, 0)

    gaussian_kernel = init_gaussian_kernel(size_gkernel)
    img_smooth = my_convolution(img, gaussian_kernel, mode='same', boundary='zero_padding')
    # cv2.imwrite("smooth.png", img_smooth)
    print("smoothed")

    sobel_kernel = init_sobel_kernel(x=True);
    edgex_img = my_convolution(img_smooth, sobel_kernel, mode='same', boundary='z')
    # cv2.imwrite("edgex_img.png", np.abs(edgex_img))
    print("found edge-x")

    sobel_kernel = init_sobel_kernel(x=False);
    edgey_img = my_convolution(img_smooth, sobel_kernel, mode='same', boundary='z')
    # cv2.imwrite("edgey_img.png", np.abs(edgey_img))
    print("found edge-y")

    edge_img = np.sqrt(edgex_img**2 + edgey_img**2)
    cv2.imwrite(output_file, edge_img)

# b = ([1, 5, 6, 6, 2],
#     [2, 1, 5, 1, 3],
#     [1, 0, 3, 5, 1],
#     [1, 3, 5, 3, 4],
#     [1, 6, 3, 2, 3])
#
# b = np.array(b)
# sobel_kernel = init_sobel_kernel(x=True)
# edgex_img = my_convolution(b, sobel_kernel, mode='same', boundary='z')
# print("edge-x:")
# print(np.array(edgex_img))
#
# sobel_kernel = init_sobel_kernel(x=False)
# edgey_img = my_convolution(b, sobel_kernel, mode='same', boundary='z')
# print("edge-y:")
# print(np.array(edgey_img))

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("--input_file", "-i", type=str, help="Path to input image")
    parser.add_argument("--output_file", "-o", type=str, help="Path to output image")
    parser.add_argument("--size", "-s", type=int, default=3, help="Size of gaussian filter")

    args = parser.parse_args()
    sobel_filer(args.input_file, args.output_file, args.size)

    """
    py ./week05_q2.py -i font.png -o output_q2.png -s 3
    """
