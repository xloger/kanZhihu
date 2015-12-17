# kanZhihu
该项目为 [看知乎]( http://www.kzhihu.com) 的安卓App。
整个项目未使用第三方框架，因此非常适合初学者参考。
以下为技术要点（顺序与难易无关），供读者自行判断是否需要：

* Log信息用自定义的MyLog类代替，在发布时可省去删除Log信息；
* 配置信息采用专门的ConfigUtil类处理，降低耦合度；
* 所有异步任务采用了回调机制；
* 对于Json的解析交由JavaBean自己解析，降低耦合度；
* 采用了三级图片缓存（文件、软引用，LRU），并解决图片错位等问题，仅需调用CacheTool的cacheImage即可；
* 对Adapter进行了抽象，开发更有效率；
* 使用了Android自带的下拉刷新SwipeRefreshLayout；
* 使用了Android5.0推出的RecyclerView与CardView；
* 用ToolBar代替了ActionBar，并且不需要每个布局手动添加ToolBar。（解决方案来自：[薄荷Toolbar(ActionBar)的适配方案](http://stormzhang.com/android/2015/08/16/boohee-toolbar/)）
* ListView的局部刷新（用于消除“未读”标记）。
