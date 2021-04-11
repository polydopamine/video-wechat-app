const app = getApp();
Page({
  data: {
    videoParams: {}
  },

  onload: function(params) {
    var me = this;
    console.log(params);
    me.setData({
      videoParams: params
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