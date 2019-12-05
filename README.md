## 项目结构
- data: 存储原始数据，词嵌入，图嵌入，ast，cfg，函数调用图
- tmp: 未整理的项目，后续会被删除

## 使用
运行克隆检测
```bash
docker run --rm -i -v /mnt/share/CloneData/data/experiment:/workspace cdetector:latest -F1 /workspace/src/1/1.c -F2 /workspace/src/1/2.c
```
查看具体功能（根据功能id查看）,{文件路径}:{功能id}
```bash
/workspace/src/1/1.c:0
```
退出
```bash
exit
```