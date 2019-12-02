import sys

import torch
import numpy as np

from Classifier import Net


def restore_net_check(x):
    data = torch.from_numpy(x).float()

    net = Net(n_feature=64, n_hidden1=10, n_hidden2=20, n_hidden3=30, n_hidden4=20, n_hidden5=10, n_output=2)

    net.load_state_dict(torch.load('checkpoint.pth'))

    out = net(data)
    prediction = torch.max(out, 1)[1]
    print(prediction.numpy()[0])


if __name__ == '__main__':
    feature_path = sys.argv[1]
    x = np.zeros((1, 64))
    with open(feature_path, 'r') as feature_file:
        line = feature_file.readline()
        cols = line.split(' ')
        for i in range(len(cols)):
            x[0][i] = float(cols[i])
    restore_net_check(x)
