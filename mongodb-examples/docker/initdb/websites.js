try {

    var docs = [{
        "name": "百度",
        "url": "https://www.baidu.com",
        "sort": 1,
        "description": "",
        "create_time": "2021-04-01 12:00:00"
    }, {
        "name": "博客园",
        "url": "https://www.cnblogs.com",
        "sort": 2,
        "description": "",
        "create_time": "2021-04-01 13:00:00"
    }, {
        "name": "菜鸟教程",
        "url": "https://www.runoob.com/mongodb/mongodb-tutorial.html",
        "sort": 3,
        "description": "",
        "create_time": "2021-04-01 14:00:00"
    }];

    db.websites.insertMany(docs)
} catch (e) {
    print (e);
}