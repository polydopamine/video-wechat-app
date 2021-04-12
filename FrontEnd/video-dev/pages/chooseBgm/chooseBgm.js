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

  upload: function() {
    var me = this;

    var duration = me.data.videoParams.duration;
    var height = me.data.videoParams.height;
    var width = me.data.videoParams.width;
    var size = me.data.videoParams.size;
    var tempFilePath = me.data.videoParams.tempFilePath;
    var thumbTempFilePath = me.data.videoParams.thumbTempFilePath;

    wx.uploadFile({
      filePath: filePath,
      name: 'file',
      url: serverUrl + "/video/upload",
      success: function(res) {
      }
    })

  }
})