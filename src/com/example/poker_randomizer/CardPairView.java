package com.example.poker_randomizer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class CardPairView extends ImageView {
	private String imgCard1;
	private String imgCard2;

	public CardPairView(Context context, String imgCard1, String imgCard2) {
		super(context);
		this.imgCard1 = imgCard1;
		this.imgCard2 = imgCard2;
	}

	public CardPairView(Context context, AttributeSet attrs, int defStyleAttr,
			String imgCard1, String imgCard2) {
		super(context, attrs, defStyleAttr);
		this.imgCard1 = imgCard1;
		this.imgCard2 = imgCard2;

	}

	public CardPairView(Context context, AttributeSet attrs, String imgCard1,
			String imgCard2) {
		super(context, attrs);
		this.imgCard1 = imgCard1;
		this.imgCard2 = imgCard2;

	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		if (imgCard2.equals("")|| imgCard2.equals("")) {
			return;
		}
		int identifierCard1 = getResources().getIdentifier(imgCard1,
				"drawable", "com.example.poker_randomizer");
		int identifierCard2 = getResources().getIdentifier(imgCard2,
				"drawable", "com.example.poker_randomizer");
		Bitmap bitmapCard1 = BitmapFactory.decodeResource(getResources(),
				identifierCard1);
		Bitmap bitmapCard2 = BitmapFactory.decodeResource(getResources(),
				identifierCard2);
		canvas.rotate(20);
		canvas.drawBitmap(bitmapCard1, new Rect(0, 0, 100, 100), new Rect(0, 0,
				100, 100), null);
		canvas.rotate(20);
		canvas.drawBitmap(bitmapCard2, new Rect(0, 0, 100, 100), new Rect(0, 0,
				100, 100), null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
