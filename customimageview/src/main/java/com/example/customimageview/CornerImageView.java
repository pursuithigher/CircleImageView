package com.example.customimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by QZhu on 16-7-26.
 */
public class CornerImageView extends ImageView{

    int resId = 0;
    int imageWidth = 0;
    int imageHeight = 0;
    Drawable idrawable = null;

    public CornerImageView(Context context) {
        this(context,null);
    }

    public CornerImageView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.CornerImage,defStyleAttr,0);
            resId = a.getResourceId(R.styleable.CornerImage_cornerdrawable,0);
            circleColor = a.getColor(R.styleable.CornerImage_circlecolor,Color.BLUE);
            circlicSize = a.getDimensionPixelSize(R.styleable.CornerImage_cornersize,5);
        Log.d("circleSize",String.valueOf(circlicSize));
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(idrawable == null)
            setDrawable(createCircleImage(resId));//,getMeasuredWidth(),getMeasuredHeight()));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private Drawable createCircleImage(int drawableId){
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeResource(getResources(),drawableId);
        return createCircleImage(bitmap);
    }

    private Drawable createCircleImage(Bitmap bitmap){
        imageWidth = bitmap.getWidth();
        imageHeight = bitmap.getHeight();

        int min = Math.min(imageHeight,imageWidth);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);

        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0 , 0, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        paint.setColor(circleColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(circlicSize);
        canvas.drawCircle(min / 2 , min/ 2 , (min-circlicSize) / 2, paint);

        idrawable = new BitmapDrawable(getResources(),target);
        return idrawable;
    }

    private int circleColor = Color.BLUE;
    private int circlicSize = 8;

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public void setCirclicSize(int circlicSize) {
        this.circlicSize = circlicSize;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        idrawable = createCircleImage(bm);
        super.setImageBitmap(bm);
    }

    @Override
    public void setImageResource(int resId) {
        setDrawable(createCircleImage(resId));
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        //super.setImageDrawable(drawable);
    }

    private void setDrawable(Drawable drawable){
        super.setImageDrawable(drawable);
    }
}
