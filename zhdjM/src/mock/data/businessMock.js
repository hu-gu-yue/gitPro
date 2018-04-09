/**
 * 商户模拟数据service
 * Created by hlf on 2017/10/24.
 */
import Mock from 'mockjs'

export default {
    getBusinessIndex:function () {
        var indexData = Mock.mock({
            "banner": [
                "camp/1.png",
                "camp/2.png",
                "camp/3.png"
            ],
            "business": {
                "title":"张家农家乐",
                "score": 3.5,
                "address": "湖南省天心区豪布斯卡"
            }
        })
        return indexData;
    },
    getBusinessStay:function () {
        var indexData = Mock.mock({
            "stays|1-4": [
                {
                    "itemId|1": [
                        13,
                        43,
                        26,
                        89
                    ],
                    "title|1": [
                        "很愉快的一次就餐",
                        "很好玩",
                        "下次再来",
                        "值得回味"
                    ],
                    "replyCount|1":[
                        "2012",
                        "24",
                        "1002",
                        "71012",
                        "123"
                    ],
                    "imgUrl|1":[
                        "camp/4.png",
                        "camp/1.png",
                        "camp/3.png"
                    ],
                    "price|1":[
                        11,
                        432,
                        252,
                        982,
                        33
                    ],
                    "type|1":[
                        1
                    ]
                }
            ]
        })
        return indexData;
    },
    getAllGoods:function () {
        var indexData = Mock.mock({
            "goods|1-4": [
                {
                    "itemId|1": [
                        13,
                        43,
                        26,
                        89
                    ],
                    "title|1": [
                        "酸菜黄鸭叫",
                        "刁子鱼",
                        "篝火晚会",
                        "双人间"
                    ],
                    "replyCount|1":[
                        "2012",
                        "242",
                        "1002",
                        "71012",
                        "123"
                    ],
                    "imgUrl|1":[
                        "camp/4.png",
                        "camp/1.png",
                        "camp/2.png",
                        "camp/3.png"
                    ],
                    "price|1":[
                        11,
                        432,
                        252,
                        982,
                        33
                    ],
                    "type|1":[
                        1,
                        2,
                        3,
                        4,
                        5
                    ]
                }
            ]
        })
        return indexData;
    },
    getBusinessFood:function () {
        var indexData = Mock.mock({
            "foods|1-4": [
                {
                    "itemId|1": [
                        23,
                        73,
                        46,
                        99
                    ],
                    "title|1": [
                        "香煎豆腐",
                        "雄鱼头",
                        "剁椒牙白",
                        "辣椒炒肉"
                    ],
                    "replyCount|1":[
                        "212",
                        "24",
                        "102",
                        "283"
                    ],
                    "imgUrl|1":[
                        "camp/4.png",
                        "camp/1.png",
                        "camp/3.png"
                    ],
                    "price|1":[
                        11,
                        432,
                        252,
                        982,
                        33
                    ],
                    "type|1":[
                        2
                    ]
                }
            ]
        })
        return indexData;
    },
    getBusinessSpecial:function () {
        var indexData = Mock.mock({
            "specials|1-4": [
                {
                    "itemId|1": [
                        13,
                        43,
                        26,
                        89
                    ],
                    "title|1": [
                        "刁子鱼",
                        "银鱼",
                        "猪血丸子",
                        "津市牛肉粉"
                    ],
                    "replyCount|1":[
                        "224",
                        "1002",
                        "723"
                    ],
                    "imgUrl|1":[
                        "camp/4.png",
                        "camp/1.png",
                        "camp/3.png"
                    ],
                    "price|1":[
                        11,
                        432,
                        252,
                        982,
                        33
                    ],
                    "type|1":[
                        4
                    ]
                }
            ]
        })
        return indexData;
    },
    getBusinessPlay:function () {
        var indexData = Mock.mock({
            "plays|1-4": [
                {
                    "itemId|1": [
                        443,
                        113,
                        226,
                        589
                    ],
                    "title|1": [
                        "篝火晚会",
                        "游湖",
                        "冲浪",
                        "KTV自助"
                    ],
                    "replyCount|1":[
                        "412",
                        "624",
                        "33",
                        "7112",
                        "123"
                    ],
                    "imgUrl|1":[
                        "camp/4.png",
                        "camp/1.png",
                        "camp/3.png"
                    ],
                    "price|1":[
                        11,
                        432,
                        252,
                        982,
                        33
                    ],
                    "type|1":[
                        3
                    ]
                }
            ]
        })
        return indexData;
    },


    businessDetail: function (param) {
        let jsObj = JSON.parse(param.body);
        let type = jsObj.type;
        let itemId = jsObj.itemId;
        console.log("---itemId----" + itemId + "--type---"  + type);

        let foodStr = Mock.mock({
            "businessDetail":
                {
                    "title": "刁子鱼",
                    "desc": "能够或比较容易观赏到湖上景色湖畔怡人风景，能够或比较容易观赏到湖上景色湖畔怡人风景",
                    "price": "232",
                    "unit": "份",
                    "suitNum":"3-4人",
                    "imgUrl": "camp/1.png"
                }
        });

        let playStr = Mock.mock({
            "businessDetail":
                {
                    "title": "篝火晚会",
                    "desc": "能够或比较容易观赏到湖上景色湖畔怡人风景，能够或比较容易观赏到湖上景色湖畔怡人风景",
                    "price": "232",
                    "unit": "场",
                    "suitNum":"100人以上",
                    "imgUrl": "camp/3.png"
                }
        });

        let specialStr = Mock.mock({
            "businessDetail":
                {
                    "title": "特产-邵东猪血丸子",
                    "desc": "当地风味",
                    "price": "23",
                    "unit": "份",
                    "suitNum":"2",
                    "imgUrl": "camp/3.png",
                    "allowMail":"是"
                }
        });

        let scenicStr = Mock.mock({
            "businessDetail":
                {
                    "title": "白鹭岛",
                    "desc": "落霞与孤鹜齐飞 秋水共长天一色",
                    "suitNum":3,
                    "imgUrl": "camp/4.png",
                    "province":"湖南",
                    "city":"娄底",
                    "strategyDesc":"好好玩...",
                    "openStartTime":"09:00",
                    "openEndTime":"17:30",
                    "suggestPlayTime":"2天"
                }
        });

        var indexData = Mock.mock({
            "banner": [
                "camp/1.png",
                "camp/2.png",
                "camp/3.png"
            ],
            "reply|4": [
                {
                    "content|1": [
                        "很愉快的一次就餐",
                        "很好玩",
                        "下次再来",
                        "值得回味"
                    ],
                    "replyTime|1":[
                        "2017-10-22",
                        "2017-10-24",
                        "2017-10-02",
                        "2017-10-12",
                        "2017-10-23"
                    ],
                    "userName|1":[
                        "古月",
                        "月半"
                    ],
                    "headImg|1":[
                        "camp/4.png",
                        "camp/1.png",
                        "camp/3.png"
                    ],
                    "businessReply|1":[
                        "感谢支持",
                        "祝您旅途愉快",
                        "么么哒",
                        "感谢"
                    ],
                    "score|1":[
                        1.2,
                        2.3,
                        3.4,
                        4.5
                    ]
                }
            ]
        })

        let dataStr = {};
        if(type == 1){
            let startDate = jsObj.startDate;
            let endDate = jsObj.endDate;
            let stayStr = Mock.mock({
                "businessDetail":
                    {
                        "title": "总统套房",
                        "desc": "能够或比较容易观赏到湖上景色湖畔怡人风景，能够或比较容易观赏到湖上景色湖畔怡人风景",
                        "price": "232",
                        "unit": "间",
                        "suitNum":3,
                        "imgUrl": "camp/4.png",
                        "startDate":startDate,
                        "endDate":endDate
                    }
            });

            dataStr = stayStr.businessDetail;
        } else if (type == 2){
            dataStr = foodStr.businessDetail;
        } else if (type == 3){
            dataStr = playStr.businessDetail;
        } else if (type == 4){
            dataStr = specialStr.businessDetail;
        } else if (type == 5){
            dataStr = scenicStr.businessDetail;
        }

        let rspData = {
            success: true,
            message: '',
            data: {
                campName: "韶湖自驾友营地",
                averageScore: 4.8 ,
                businessDetail: dataStr,
                indexData:indexData,
            },
        };
        return rspData;
    }
}
