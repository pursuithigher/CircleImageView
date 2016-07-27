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
            hascirclic = a.getBoolean(R.styleable.CornerImage_hascirclic,true);
            setCircleFactor(a.getFloat(R.styleable.CornerImage_circlefactor,ROUNDFACTOR));
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
        canvas.drawCircle(min / 2, min / 2, (min / 2)*circleFactor, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, (imageWidth-min)/2 , -(imageHeight-min)/2, paint);

        if(hascirclic)
        {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            paint.setColor(circleColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(circlicSize);
            canvas.drawCircle(min / 2 , min/ 2 , (min*circleFactor/2-circlicSize/2), paint);
        }

        idrawable = new BitmapDrawable(getResources(),target);
        return idrawable;
    }

    public final static float RECTFACTOR = (float)Math.sqrt(2);
    public final static float ROUNDFACTOR  = 1f;

    private int circleColor = Color.BLUE;
    private int circlicSize = 8;

    //whether has circlic
    private boolean hascirclic = true;

    //circle factor 0.5(round)~1(rect)
    private float circleFactor = ROUNDFACTOR;

    /**
     * set the circleFcctor between {@link CornerImageView#ROUNDFACTOR} ~ {@link CornerImageView#RECTFACTOR}
     * @param circleFactor
     */
    public void setCircleFactor(float circleFactor) {
        if(circleFactor <= ROUNDFACTOR)
        {
            this.circleFactor = ROUNDFACTOR;
        }else{
            this.circleFactor = (circleFactor > RECTFACTOR) ? RECTFACTOR : circleFactor;
            setHasCirclic(false);
        }
    }

    public CornerImageView setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        return this;
    }

    public CornerImageView setCirclicSize(int circlicSize) {
        this.circlicSize = circlicSize;
        return this;
    }

    public CornerImageView setHasCirclic(boolean hascirclic) {
        this.hascirclic = hascirclic;
        return this;
    }

    public void apply(){
        if(resId != 0)
        {
            setDrawable(createCircleImage(resId));
        }
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
