package com.almeros.android.multitouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.almeros.android.multitouch.model.SeatMo;

/**
 * @author captain_miao
 */
public class SeatTableView extends View {
    Bitmap seat_sale, seat_sold, seat_selected;
    Bitmap SeatSale, SeatSold, SeatSelected;

    private int seatWidth;//座位图的宽,长宽相同
    private int defWidth;//初始值

    //放大率和移动位置
    public float mScaleFactor = 1.f;
    public float mPosX = .0f;
    public float mPosY = .0f;


    private SeatMo[][] seatTable;
    private int rowSize;
    private int columnSize;

    private Paint linePaint;//中央线的绘制



    public SeatTableView(Context context) {
        super(context, null);

    }

    public SeatTableView(Context context, AttributeSet attr) {
        super(context, attr);

        SeatSale = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.seat_sale);
        SeatSold = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.seat_sold);
        SeatSelected = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.seat_selected);


        setVerticalScrollBarEnabled(true);
        setHorizontalScrollBarEnabled(true);

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) (defWidth * mScaleFactor) * columnSize +  (int)mPosX,
                (int) (defWidth * mScaleFactor) * rowSize + (int)mPosY);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(defWidth < 10) {
            throw new IllegalArgumentException("the width must > 10, the value is " + defWidth);
        }
        seatWidth = (int) (defWidth * mScaleFactor);
        seatWidth = (int) (defWidth * mScaleFactor);


        // 可购买座位
        seat_sale = Bitmap.createScaledBitmap(SeatSale, seatWidth, seatWidth, true);
        // 红色 已售座位
        seat_sold = Bitmap.createScaledBitmap(SeatSold, seatWidth, seatWidth, true);
        // 绿色 我的选择
        seat_selected = Bitmap.createScaledBitmap(SeatSelected, seatWidth, seatWidth, true);

        // 画座位
        for (int i = 0; i < rowSize; i++) {

            for (int j = 0; j < columnSize; j++) {
                //绘制中线,座位间隔由图片来做,简化处理
                if (linePaint != null) {
                    if ((columnSize % 2 == 0 && j == columnSize / 2)) {
                        canvas.drawLine((j * (seatWidth)) + mPosX - linePaint.getStrokeWidth() / 2, i * (seatWidth) + +mPosY,
                                (j * (seatWidth)) + mPosX, i * (seatWidth) + seatWidth + mPosY, linePaint);
                    } else if (columnSize % 2 == 1 && j - 1 == columnSize / 2) {
                        canvas.drawLine((j * (seatWidth)) + mPosX, i * (seatWidth) + +mPosY,
                                (j * (seatWidth)) + mPosX, i * (seatWidth) + seatWidth + mPosY, linePaint);
                    }
                }


                switch ( seatTable[i][j].status ){
                    case 0: {
                        canvas.drawBitmap(seat_sold, j * (seatWidth) + mPosX, i * (seatWidth) + mPosY, null);
                        break;
                    }
                    case 1: {
                        canvas.drawBitmap(seat_sale, j * (seatWidth) + mPosX, i * (seatWidth) + mPosY, null);
                        break;
                    }
                    case 2: {
                        canvas.drawBitmap(seat_selected, j * (seatWidth) + mPosX, i * (seatWidth) + mPosY, null);
                        break;
                    }
                    default:{
                        canvas.drawBitmap(seat_sold, j * (seatWidth) + mPosX, i * (seatWidth) + mPosY, null);
                        break;
                    }

                }

            }
        }

    }



    public void setSeatTable(SeatMo[][] seatTable) {
        this.seatTable = seatTable;
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }


    public void setLinePaint(Paint linePaint) {
        this.linePaint = linePaint;
    }

    public void setDefWidth(int defWidth) {
        this.defWidth = defWidth;
    }

    public int getSeatWidth() {
        return seatWidth;
    }

}
