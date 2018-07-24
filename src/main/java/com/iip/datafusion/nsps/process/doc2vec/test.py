"""
import numpy as np
import pickle as pkl
import os

PATH = 'DATA/'
dit = 'data/newsblogbbs.vec'
dit2 = 'data/dtest.pkl'


def sub_dict(path):
    files = os.listdir(PATH)
    files_ = []
    files_n = []
    for f in files:
        if os.path.isfile(PATH + '/' + f):
            files_n.append(PATH + '/' + f)
            with open(PATH + '/' + f, 'r') as f2:
                files_.append([line.strip() for line in f2.readlines()])
    vocab = set()
    files_w = []
    ret = np.zeros(shape=(len(files_), 200), dtype='float32')
    # with open('data/test', 'r') as f:
    #     R = [line.strip() for line in f.readlines()]
    for f in files_:
        words = []
        for t in f:
            spt = t.split()
            for word in spt:
                vocab.add(word)
                words.append(word)
        files_w.append(words)
    print(len(vocab))
    word_vecs = {}
    with open(dit, 'r') as f:
        vocab_size, layer1_size = map(int, f.readline().split())
        print(vocab_size)
        for i in range(vocab_size):
            tmp = f.readline().split()
            if tmp[0] not in vocab: continue
            word_vecs[tmp[0]] = np.asarray(tmp[1:], dtype='float32')
            if i % 5000 == 0:
                print(i)
    with open(dit2, 'wb') as f:
        pkl.dump(word_vecs, f)
        print(len(word_vecs))

    c2 = 0
    for f in files_w:
        c = 0
        for word in f:
            if word in word_vecs:
                ret[c2] += word_vecs[word]
                c += 1
        ret[c2] /= c
        c2 += 1
    res = {}
    c3 = 0
    for f in files_n:
        res[f] = ret[c3]
        c3 += 1
    print(res)
    return res
"""
def hello(path):
    res = {}
    res['f1'] = str([1.,2.,3.,4.,5.])
    res['f2'] = str([1.,2.,3.,5.,6.])
    res['f3'] = str([1.,2.,3.,6.,7.])
    print('path', path)
    return res