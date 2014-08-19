package com.example.poker_randomizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class CardPairView extends ImageView {
	private String imgCard1="back";
	private String imgCard2="back";

	public CardPairView(Context context) {
		super(context);
		
	}

	public CardPairView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		

	}

	public CardPairView(Context context, AttributeSet attrs) {
		super(context, attrs);
		

	}
	
	public void init(String imgCard1,String imgCard2){
		this.imgCard1=imgCard1;
		this.imgCard2=imgCard2;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		Log.d("poker_randomizer","OnDraw was called");
		//calculate the dimension of the cards
		int heightCard=this.getHeight()-20;
		int widthCards=(int)(heightCard*0.719);
		//if (imgCard2.equals("")|| imgCard2.equals("")) {
		//	return;
		//}
		int identifierCard1 = getResources().getIdentifier(imgCard1,
				"drawable", "com.example.poker_randomizer");
		int identifierCard2 = getResources().getIdentifier(imgCard2,
			"drawable", "com.example.poker_randomizer");
		Bitmap bitmapCard1 = BitmapFactory.decodeResource(getResources(),
				identifierCard1);
		//Scale the bitmap
		Bitmap scaledBitmapCard1=Bitmap.createScaledBitmap(bitmapCard1, widthCards, heightCard, false);
		
		//canvas.rotate();
		//canvas.drawBitmap(scaledBitmapCard1, new Rect(0, 0, this.getWidth(),this.getHeight()), new Rect(0, 0,
			//	 this.getWidth(),this.getHeight()), null);
		canvas.drawBitmap(scaledBitmapCard1, 0,10, null);
		bitmapCard1 = BitmapFactory.decodeResource(getResources(),
				identifierCard2);
		scaledBitmapCard1=Bitmap.createScaledBitmap(bitmapCard1, widthCards,heightCard, false);
		
		//canvas.rotate(20,0,heightCard);
		canvas.drawBitmap(scaledBitmapCard1,30,10, null);
	}

//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		    int desiredWidth = 100;
//		    int desiredHeight = 100;
//
//		    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//		    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//		    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//		    int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//		    int width;
//		    int height;
//
//		    //Measure Width
//		    if (widthMode == MeasureSpec.EXACTLY) {
//		        //Must be this size
//		        width = widthSize;
//		    } else if (widthMode == MeasureSpec.AT_MOST) {
//		        //Can't be bigger than...
//		        width = Math.min(desiredWidth, widthSize);
//		    } else {
//		        //Be whatever you want
//		        width = desiredWidth;
//		    }
//
//		    //Measure Height
//		    if (heightMode == MeasureSpec.EXACTLY) {
//		        //Must be this size
//		        height = heightSize;
//		    } else if (heightMode == MeasureSpec.AT_MOST) {
//		        //Can't be bigger than...
//		        height = Math.min(desiredHeight, heightSize);
//		    } else {
//		        //Be whatever you want
//		        height = desiredHeight;
//		    }
//
//		    //MUST CALL THIS
//		    setMeasuredDimension(width, height);
//	}

}
