# THULAC Analysis for Elasticsearch 
采用[THULAC](https://github.com/thunlp/THULAC-Java)实现的[Elasticsearch](https://www.elastic.co)中文分词插件。

版本
--------

Plugin 版本 | ES 版本 | THULAC 版本 |  Link
-----------|-----------|----------|------------
master | 7.x -> master | lite      |
7.9.1  | 7.9.1         | lite      |[下载](https://github.com/microbun/elasticsearch-thulac-plugin/releases/download/7.9.1/elasticsearch-thulac-plugin-7.9.1.zip)
6.4.1-181027 | 6.4.1          | lite      |[下载](https://github.com/microbun/elasticsearch-thulac-plugin/releases/download/6.4.1-181027/elasticsearch-thulac-plugin-6.4.1-181027.zip)
6.4.0-181027 | 6.4.0          | lite      |[下载](https://github.com/microbun/elasticsearch-thulac-plugin/releases/download/6.4.0-181027/elasticsearch-thulac-plugin-6.4.0-181027.zip)
6.3.0-181027 | 6.3.0          | lite      |[下载](https://github.com/microbun/elasticsearch-thulac-plugin/releases/download/6.3.0-181027/elasticsearch-thulac-plugin-6.3.0-181027.zip)
6.2.0-181027 | 6.2.0          | lite      |[下载](https://github.com/microbun/elasticsearch-thulac-plugin/releases/download/6.2.0-181027/elasticsearch-thulac-plugin-6.2.0-181027.zip)
6.1.0-181027 | 6.1.0          | lite      |[下载](https://github.com/microbun/elasticsearch-thulac-plugin/releases/download/6.1.0-181027/elasticsearch-thulac-plugin-6.1.0-181027.zip)


下载安装
--------
直接下载已经打包好的插件，解压到elasticsearch的plugins目录下即可。

编译安装
--------
1.编译打包 

```bash
git clone git@github.com:microbun/elasticsearch-thulac-plugin.git
cd elasticsearch-thulac-plugin
./gradlew release
```

2.安装到elasticsearch
```
cp build/distributions/elasticsearch-thulac-plugin-7.9.1.zip ${ES_HOME}/plugins
cd ${ES_HOME}/plugins
unzip elasticsearch-thulac-plugin-7.9.1.zip
rm elasticsearch-thulac-plugin-7.9.1.zip
```
解压后在plugins目录下会有一个thulac文件夹。
```
thulac
 |-elasticsearch-thulac-plugin-7.9.1.jar
 |-models #算法模型目录
 |-plugin-descriptor.properties
 |-plugin.xml
```

3.由于THULAC的模型太大，插件中没有包含模型数据，可以在[THULAC](https://github.com/thunlp/THULAC-Java) 下载模型(lite)，将模型拷贝到models中。


示例
--------
#### 1.创建索引

1.1 使用默认分词方式
```bash
curl -H "Content-Type:application/json" -XPUT http://localhost:9200/index -d'
{
  "mappings": {
    "properties": {
      "text": {
        "type": "text",
        "analyzer": "thulac"
      }
    }
  }
}
'
```

1.2 自定义分词器
```bash
curl -H "Content-Type:application/json" -XPUT http://localhost:9200/index -d'
{
  "settings": {
    "analysis": {
      "tokenizer": {
        "custom_thulac_tokenizer": {
          "type": "thulac",
          "user_dict": "userdict.txt",
          "t2s": true,
          "filter": false
        }
      },
      "analyzer": {
        "custom_thulac_analyzer": {
          "tokenizer": "custom_thulac_tokenizer",
          "filter": [
            "lowercase"
          ]
        }
      }
    }
  },
  "mappings": {
    "properties": {
      "text": {
        "type": "text",
        "analyzer": "custom_thulac_analyzer"
      }
    }
  }
}'
```

| 参数名称 | 含义 | 值 |
| --- | --- |---|
| t2s | 将句子从繁体转化为简体。默认：true | false/true |
| filter | 使用过滤器去除一些没有意义的词语，例如“可以”。默认：false | false/true |
| user_dict | 自定义词典路径，每一个词一行，UTF8编码，相对路径和绝对路径.</br>相对路径：userdict.txt 会加载 ${ES_HOME}/plugins/module/userdict.txt文件</br>绝对路径：/home/elasticsearch/userdict.txt</br>默认：userdict.txt |  |

#### 2.查看索引
```bash
curl http://localhost:9200/index
```

#### 3.测试分词效果
```bash
curl -H "Content-Type:application/json"  -XPOST http://localhost:9200/index/_analyze -d'
{
 "analyzer":"thulac", 
 "text":"我是中国人"
}
'
```

#### 4.删除索引
```
curl -XDELETE http://localhost:9200/index
```
