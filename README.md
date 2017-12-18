# THULAC Analysis for Elasticsearch 
采用[THULAC](https://github.com/thunlp/THULAC-Java)实现的[Elasticsearch](https://www.elastic.co)中文分词插件。

版本
--------

Plugin 版本 | ES 版本 | THULAC 版本
-----------|-----------|-----------
master | 6.x -> master | 1.2
6.1.0 | 6.1.0          | 1.2

安装
--------
1.编译打包

```bash
git clone git@github.com:microbun/elasticsearch-thulac-plugin.git
cd elasticsearch-thulac-plugin
./gradlew release
```

2.安装到elasticsearch
```
cp build/distributions/elasticsearch-thulac-plugin-6.1.0.zip ${ES_HOME}/plugins
cd ${ES_HOME}/plugins
unzip elasticsearch-thulac-plugin-6.1.0.zip
rm elasticsearch-thulac-plugin-6.1.0.zip
```
解压后在plugins目录下会有一个thulac文件夹。
```
thulac
 |-elasticsearch-thulac-plugin-6.1.0.jar
 |-models #算法模型目录
 |-plugin-descriptor.properties
 |-plugin.xml
```

3.由于THULAC的算法模型太大，插件中没有包含模型数据，可以在在[THULAC](https://github.com/thunlp/THULAC-Java) 下载算法模型(v1_2)，算法模型拷贝到models中。


示例
--------
1.创建索引
```bash
curl -H "Content-Type:application/json" -XPUT http://localhost:9200/index -d'
{
  "settings": {
    "analysis": {
      "analyzer": {
        "thulac_ana": {
          "tokenizer": "thulac",
          "filter": [
            "lowercase"
          ]
        }
      }
    }
  }
}
'
```
2.查看索引
```bash
curl http://localhost:9200/index
```
3.测试分词效果
```bash
curl -H "Content-Type:application/json"  -XPOST http://localhost:9200/index/_analyze -d'
{
 "analyzer":"thulac_ana",
 "text":"我是中国人"
}
'

```
4.删除索引
```
curl -XDELETE http://localhost:9200/index
```