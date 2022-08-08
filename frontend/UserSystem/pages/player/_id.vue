<template>
  <div>
    <!-- 阿里云视频播放器样式 -->
    <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css" >
    <!-- 阿里云视频播放器脚本 -->
    <script charset="utf-8" type="text/javascript" src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js" />
    <!-- 定义播放器dom -->
    <div id="J_prismPlayer" class="prism-player" />
  </div>
</template>

<script>
import vodApi from '@/api/vod';
export default {
    layout: 'video',  // 应用video布局
    asyncData({ params, error }) {
        return vodApi.getPlayAuth(params.id)
                    .then(response => {
                        return {
                            playAuth: response.data.data.playAuth,
                            videoId: params.id
                        }
                    });
    },
    mounted() {  // 在页面渲染之后执行
        new Aliplayer({
            id: 'J_prismPlayer',
            vid: this.videoId,              // 视频id
            playauth: this.playAuth,        // 播放凭证
            encryptType: '1',               // 如果播放加密视频，则该项需要设置为1；如果播放非加密视频，则无需设置此项
            width: '100%',
            height: '500px',

            // 可选配置
            cover: 'http://guli.shop/photo/banner/1525939573202.jpg',  // 视频封面
            qualitySort: 'asc',             // 清晰度排序
            mediaType: 'video',             // 播放媒体（视频或者音频）
            autoplay: false,                // 自动播放
            isLive: false,                  // 直播
            rePlay: false,                  // 循环播放
            preload: true,
            controlBarVisibility: 'hover',  // 视频控制条显示：鼠标悬停
            useH5Prism: true,               // 播放器类型：html5
        }, function(player) {
            console.log('创建播放器成功');
        });
    }
}
</script>