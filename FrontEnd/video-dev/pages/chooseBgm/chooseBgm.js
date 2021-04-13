const app = getApp();
Page({
  data: {
    "minioUrl": "",
    "videoParams": {},
    "bgmList": []
  },

  onLoad: function(params) {
    var me = this;
    // 获取上个页面带来的视频参数
    console.log(params);
    me.setData({
      videoParams: params
    });
    // 获取bgm列表
    var serverUrl = app.serverUrl;
    var minioUrl = app.minioUrl;
    wx.request({
      url: serverUrl + "/bgm/list",
      method: "GET",
      success: function(res) {
        var bgmList = res.data.data;
        me.setData({
            "bgmList": bgmList,
            "minioUrl": minioUrl
        });
      }
    });

  },

  upload: function(e) {
    var me = this;

    var duration = me.data.videoParams.duration;
    var height = me.data.videoParams.height;
    var width = me.data.videoParams.width;
    var size = me.data.videoParams.size;
    var tempFilePath = me.data.videoParams.tempFilePath;
    var thumbTempFilePath = me.data.videoParams.thumbTempFilePath;

    var userId = app.getGlobalUserInfo().id;
    var audioId = e.detail.value.desc;
    var videoDesc = e.detail.value.bgmId;
    var serverUrl = app.serverUrl;

    wx.showLoading({
      title: "上传中..."
    });

    wx.uploadFile({
      filePath: tempFilePath,
      name: 'file',
      url: serverUrl + "/video/upload",
      formData: {
        "userId": userId,
        "audioId": audioId,
        "videoDesc": videoDesc,
        "videoSeconds": duration,
        "videoWidth": width,
        "videoHeight": height
      },
      success: function(res) {
        console.log(res);
        wx.hideLoading();
        var data = JSON.parse(res.data);
        if (data.status == 200) {
          wx.showToast({
            title: "上传成功！",
            icon: "none",
            duration: 3000
          });
          wx.redirectTo({
            url: '../mine/mine',
          })
        } else if (data.status == 500) {
          wx.showToast({
            title: res.data.msg,
            icon: "none",
            duration: 3000
          });
        }
      }
    });

  }
})