import torch.nn as nn
import torch
import numpy as np
import pandas as pd
import os
import torch.nn.functional as F

os.environ["CUDA_VISIBLE_DEVICES"] = "0"


def get_data(csv_path):
    csv_data = pd.read_csv(csv_path, sep=',', header=None)
    data = csv_data.values.astype(np.float)[:, 0:32]
    label = csv_data.values.astype(np.int)[:, 32:]

    total_data = np.hstack((data, label))

    np.random.shuffle(total_data)

    total_size = len(total_data)
    train_size = int(0.8 * total_size)
    # test_size = total_size - train_size

    train_data = torch.from_numpy(total_data[0:train_size, :-1]).float()
    test_data = torch.from_numpy(total_data[train_size:, :-1]).float()
    train_label = torch.from_numpy(total_data[0:train_size, -1]).long()
    test_label = torch.from_numpy(total_data[train_size:, -1]).long()
    # test_label = torch.from_numpy(total_data[train_size:, -1].reshape(-1, 1)).int()

    return train_data, test_data, train_label, test_label


class Net(nn.Module):
    def __init__(self, n_feature, n_hidden1, n_hidden2, n_output):
        super(Net, self).__init__()
        self.hidden1 = torch.nn.Linear(n_feature, n_hidden1)
        self.hidden2 = torch.nn.Linear(n_hidden1, n_hidden2)
        self.out = torch.nn.Linear(n_hidden2, n_output)

    def forward(self, x):
        x = F.relu(self.hidden1(x))  # activation function for hidden layer
        x = F.relu(self.hidden2(x))
        x = self.out(x)
        return x


if __name__ == '__main__':
    net = Net(n_feature=32, n_hidden1=20, n_hidden2=10, n_output=2)
    print(net)
    # optimize parameter
    optimizer = torch.optim.Adam(net.parameters(), lr=0.001)
    # calculate loss
    loss_func = torch.nn.CrossEntropyLoss()

    train_data, test_data, train_label, test_label = \
        get_data('G:\\share\\CloneDetection\\script\\data\\b_3,12,7,6,5,9,10,13,2,1_train_ident_word2vec.csv')

    for epoch in range(1000):
        out = net(train_data)
        loss = loss_func(out, train_label)

        optimizer.zero_grad()
        loss.backward()
        optimizer.step()

        if epoch % 200 == 0:
            prediction = torch.max(out, 1)[1]
            pred_y = prediction.data.numpy()
            target_y = train_label.data.numpy()
            accuracy = float((pred_y == target_y).astype(int).sum()) / float(target_y.size)
            print("accuracy=", accuracy)

    out = net(test_data)
    prediction = torch.max(out, 1)[1]

    pred_y = prediction.data.numpy()
    target_y = test_label.data.numpy()
    print(pred_y)
    print(target_y)

    accuracy = float((pred_y == target_y).astype(int).sum()) / float(target_y.size)
    print("accuracy=", accuracy)
