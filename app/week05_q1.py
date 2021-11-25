"""
Name: Lưu Văn Vương
Class: INT3404 3
MSSV: 18021446

q_1: Sử dụng file week05_q1.py. Cài đặt bộ lọc mean/median/sharpen, không sử dụng các hàm có sẵn. Sử dụng các file ảnh
ví dụ từ các bài tập trước (font.png)
"""

import cv2
import numpy as np
import argparse
import time


def conv_sum(a, b):
    s = 0
    for i in range(len(a)):
        for j in range(len(a[0])):
            s += a[i][j] * b[i][j]
    return s


def my_convolution(I, g, mode='valid', boundary='zero_padding'):
    h, w = len(g), len(g[0])
    H, W = I.shape[0], I.shape[1]

    if mode == 'valid':
        output_h = H - (h - 1)
        output_w = W - (w - 1)
    else:
        output_h = H
        output_w = W

    output = [[0 for _ in range(output_w)] for _ in range(output_h)]
    for i in range(output_h):
        for j in range(output_w):
            output[i][j] = conv_sum(I[i:i + h, j:j + w], g)

    return output


def median(arr):
    a = []
    for i in range(len(arr)):
        for j in range(len(arr[0])):
            a.append(arr[i][j])

    a.sort()
    return a[int(len(a) / 2)]


def init_mean_kernel(sz=3):
    s = sz * sz
    g = [[1.0 / s for i in range(sz)] for i in range(sz)]
    return g


def init_sharpen_kernel(sz=3, alpha=0.2):
    s = sz * sz
    center = int(sz / 2)
    original = [[0 for i in range(sz)] for i in range(sz)]
    original[center][center] = 1

    g = init_mean_kernel(sz)
    g = [[(original[i][j] + alpha * (original[i][j] - g[j][j])) for i in range(sz)] for j in range(sz)]
    print(np.array(g))
    return g


def mean_filter(input_file, output_file, kernel_size=3):
    start_time = time.time()
    # Read input file with gray value
    img = cv2.imread(input_file, 0)
    g = init_mean_kernel(kernel_size)

    output_img = my_convolution(img, g)

    # for input/output
    cv2.imwrite(output_file, np.array(output_img))
    run_time = time.time() - start_time
    print("Run mean_filter in: %.2f s" % run_time)


def median_filter(input_file, output_file, kernel_size=3):
    start_time = time.time()
    # Read input file with gray value
    img = cv2.imread(input_file, 0)

    h = w = kernel_size
    H, W = img.shape[0], img.shape[1]

    output_h = H - (h - 1)
    output_w = W - (w - 1)

    output = [[0 for _ in range(output_w)] for _ in range(output_h)]
    for i in range(output_h):
        for j in range(output_w):
            output[i][j] = median(img[i:i + h, j:j + w])

    # for input/output
    cv2.imwrite(output_file, np.array(output))
    run_time = time.time() - start_time
    print("Run median_filter in: %.2f s" % run_time)


def sharpen_filter(input_file, output_file, kernel_size=3, alpha=0.2):
    start_time = time.time()
    # Read input file with gray value
    img = cv2.imread(input_file, 0)
    g = init_sharpen_kernel(kernel_size, alpha)

    output_img = my_convolution(img, g)

    # for input/output
    cv2.imwrite(output_file, np.array(output_img))
    run_time = time.time() - start_time
    print("Run sharpen_filter in: %.2f s" % run_time)


# mean_filter('font.png', 'out_mean.png', 3)
# sharpen_filter('font.png', 'out_sharpen.png', 3, 0.3)
# median_filter("font.png", "out_median.png", 5)

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument("--input_file", "-i", type=str, help="Path to input image")
    parser.add_argument("--output_file", "-o", type=str, help="Path to output image")
    parser.add_argument("--filter_type", "-t", type=str, default='mean', help="One of mean/median/sharpness")
    parser.add_argument("--size", "-s", type=int, default=3, help="Size of filter")
    parser.add_argument("--alpha", "-a", type=float, default=0.2, help="Strength of sharpen operator")

    args = parser.parse_args()
    if args.filter_type == 'mean':
        mean_filter(args.input_file, args.output_file, args.size)
    elif args.filter_type == 'median':
        median_filter(args.input_file, args.output_file, args.size)
    else:  # args.filter_type == 'sharpen':
        sharpen_filter(args.input_file, args.output_file, args.size, args.alpha)

    """
    py week05_q1.py -i font.png -o out_mean.png -t mean -s 3
    py week05_q1.py -i font.png -o out_median.png -t median -s 3
    py week05_q1.py -i font.png -o out_sharpen.png -t sharpen -s 3 -a 0.3
    """
