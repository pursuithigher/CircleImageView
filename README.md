# CircleImageView
圆形图片
a Circle ImageView which is extends ImageView and change its drawable in some place

##Useage
1) add values->attrs.xml into your project<br/>
2) add CornerImageView.java into your moudle<br/>

##xml useage
 添加命名空间(namespace)<br/>
 such as :xmlns:custom = "http://schemas.android.com/apk/res-auto"

##custom attrs useage
 <com.example.customimageview.CornerImageView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        custom:cornerdrawable="@drawable/ic_launcher"
        custom:cornersize="4dp"
        custom:circlecolor="#00ff00"
        android:layout_centerInParent="true"
        />
<br/>
1)custom:cornerdrawable: 需要裁剪的图片<br/>
2)custom:cornersize: 圆环的大小,default:5px<br/>
3)custom:circlecolor: 圆环的颜色,default:Blue<br/>

##Public function:
setCircleColor(int );<br/>
setCirclicSize(int );

##Others:
see the fucking code
