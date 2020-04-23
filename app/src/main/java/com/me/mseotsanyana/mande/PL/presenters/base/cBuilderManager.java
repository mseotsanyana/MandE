package com.me.mseotsanyana.mande.PL.presenters.base;

import android.graphics.Color;
import android.util.Log;
import android.util.Pair;

import com.me.mseotsanyana.bmblibrary.BoomButtons.OnBMClickListener;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cButtonPlaceEnum;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cHamButton;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cSimpleCircleButton;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cTextInsideCircleButton;
import com.me.mseotsanyana.bmblibrary.BoomButtons.cTextOutsideCircleButton;
import com.me.mseotsanyana.bmblibrary.Piece.cPiecePlaceEnum;
import com.me.mseotsanyana.bmblibrary.cUtil;
import com.me.mseotsanyana.mande.R;

import java.util.ArrayList;
import java.util.List;

public class cBuilderManager {
    private static String TAG = cBuilderManager.class.getSimpleName();

    private static int[] imageResources = new int[]{
            R.drawable.bat,
            R.drawable.bear,
            R.drawable.bee,
            R.drawable.butterfly,
            R.drawable.cat,
            R.drawable.deer,
            R.drawable.dolphin,
            R.drawable.eagle,
            R.drawable.horse,
            R.drawable.elephant,
            R.drawable.owl,
            R.drawable.peacock,
            R.drawable.pig,
            R.drawable.rat,
            R.drawable.snake,
            R.drawable.squirrel
    };

    private static int imageResourceIndex = 0;

    public static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }

    public static cSimpleCircleButton.Builder getSimpleCircleButtonBuilder() {
        return new cSimpleCircleButton.Builder()
                .normalImageRes(getImageResource());
    }
/*
    public static cSimpleCircleButton.Builder getSquareSimpleCircleButtonBuilder() {
        return new cSimpleCircleButton.Builder()
                .isRound(false)
                .shadowCornerRadius(cUtil.dp2px(20))
                .buttonCornerRadius(cUtil.dp2px(20))
                .normalImageRes(getImageResource());
    }
*/
    public static cTextInsideCircleButton.Builder getSquareSimpleCircleButtonBuilder() {
        return new cTextInsideCircleButton.Builder()
                .isRound(false)
                .shadowCornerRadius(cUtil.dp2px(20))
                .buttonCornerRadius(cUtil.dp2px(20))
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_inside_circle_button_text_normal)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        Log.d(TAG,"Clicked " + index);
                    }
                });
    }

    static cTextInsideCircleButton.Builder getTextInsideCircleButtonBuilder() {
        return new cTextInsideCircleButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_inside_circle_button_text_normal);
    }

    static cTextInsideCircleButton.Builder getSquareTextInsideCircleButtonBuilder() {
        return new cTextInsideCircleButton.Builder()
                .isRound(false)
                .shadowCornerRadius(cUtil.dp2px(10))
                .buttonCornerRadius(cUtil.dp2px(10))
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_inside_circle_button_text_normal);
    }

    static cTextInsideCircleButton.Builder getTextInsideCircleButtonBuilderWithDifferentPieceColor() {
        return new cTextInsideCircleButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_inside_circle_button_text_normal)
                .pieceColor(Color.WHITE);
    }

    static cTextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilder() {
        return new cTextOutsideCircleButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_outside_circle_button_text_normal);
    }

    static cTextOutsideCircleButton.Builder getSquareTextOutsideCircleButtonBuilder() {
        return new cTextOutsideCircleButton.Builder()
                .isRound(false)
                .shadowCornerRadius(cUtil.dp2px(15))
                .buttonCornerRadius(cUtil.dp2px(15))
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_outside_circle_button_text_normal);
    }

    static cTextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilderWithDifferentPieceColor() {
        return new cTextOutsideCircleButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_outside_circle_button_text_normal)
                .pieceColor(Color.WHITE);
    }

    public static cHamButton.Builder getHamButtonBuilder() {
        return new cHamButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_ham_button_text_normal)
                .subNormalTextRes(R.string.text_ham_button_sub_text_normal);
    }

    static cHamButton.Builder getHamButtonBuilder(String text, String subText) {
        return new cHamButton.Builder()
                .normalImageRes(getImageResource())
                .normalText(text)
                .subNormalText(subText);
    }

    static cHamButton.Builder getPieceCornerRadiusHamButtonBuilder() {
        return new cHamButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_ham_button_text_normal)
                .subNormalTextRes(R.string.text_ham_button_sub_text_normal);
    }

    static cHamButton.Builder getHamButtonBuilderWithDifferentPieceColor() {
        return new cHamButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_ham_button_text_normal)
                .subNormalTextRes(R.string.text_ham_button_sub_text_normal)
                .pieceColor(Color.WHITE);
    }

    static List<String> getCircleButtonData(ArrayList<Pair> piecesAndButtons) {
        List<String> data = new ArrayList<>();
        for (int p = 0; p < cPiecePlaceEnum.values().length - 1; p++) {
            for (int b = 0; b < cButtonPlaceEnum.values().length - 1; b++) {
                cPiecePlaceEnum piecePlaceEnum = cPiecePlaceEnum.getEnum(p);
                cButtonPlaceEnum buttonPlaceEnum = cButtonPlaceEnum.getEnum(b);
                if (piecePlaceEnum.pieceNumber() == buttonPlaceEnum.buttonNumber()
                        || buttonPlaceEnum == cButtonPlaceEnum.Horizontal
                        || buttonPlaceEnum == cButtonPlaceEnum.Vertical) {
                    piecesAndButtons.add(new Pair<>(piecePlaceEnum, buttonPlaceEnum));
                    data.add(piecePlaceEnum + " " + buttonPlaceEnum);
                    if (piecePlaceEnum == cPiecePlaceEnum.HAM_1
                            || piecePlaceEnum == cPiecePlaceEnum.HAM_2
                            || piecePlaceEnum == cPiecePlaceEnum.HAM_3
                            || piecePlaceEnum == cPiecePlaceEnum.HAM_4
                            || piecePlaceEnum == cPiecePlaceEnum.HAM_5
                            || piecePlaceEnum == cPiecePlaceEnum.HAM_6
                            || piecePlaceEnum == cPiecePlaceEnum.Share
                            || piecePlaceEnum == cPiecePlaceEnum.Custom
                            || buttonPlaceEnum == cButtonPlaceEnum.HAM_1
                            || buttonPlaceEnum == cButtonPlaceEnum.HAM_2
                            || buttonPlaceEnum == cButtonPlaceEnum.HAM_3
                            || buttonPlaceEnum == cButtonPlaceEnum.HAM_4
                            || buttonPlaceEnum == cButtonPlaceEnum.HAM_5
                            || buttonPlaceEnum == cButtonPlaceEnum.HAM_6
                            || buttonPlaceEnum == cButtonPlaceEnum.Custom) {
                        piecesAndButtons.remove(piecesAndButtons.size() - 1);
                        data.remove(data.size() - 1);
                    }
                }
            }
        }
        return data;
    }

    static List<String> getHamButtonData(ArrayList<Pair> piecesAndButtons) {
        List<String> data = new ArrayList<>();
        for (int p = 0; p < cPiecePlaceEnum.values().length - 1; p++) {
            for (int b = 0; b < cButtonPlaceEnum.values().length - 1; b++) {
                cPiecePlaceEnum piecePlaceEnum = cPiecePlaceEnum.getEnum(p);
                cButtonPlaceEnum buttonPlaceEnum = cButtonPlaceEnum.getEnum(b);
                if (piecePlaceEnum.pieceNumber() == buttonPlaceEnum.buttonNumber()
                        || buttonPlaceEnum == cButtonPlaceEnum.Horizontal
                        || buttonPlaceEnum == cButtonPlaceEnum.Vertical) {
                    piecesAndButtons.add(new Pair<>(piecePlaceEnum, buttonPlaceEnum));
                    data.add(piecePlaceEnum + " " + buttonPlaceEnum);
                    if (piecePlaceEnum.getValue() < cPiecePlaceEnum.HAM_1.getValue()
                            || piecePlaceEnum == cPiecePlaceEnum.Share
                            || piecePlaceEnum == cPiecePlaceEnum.Custom
                            || buttonPlaceEnum.getValue() < cButtonPlaceEnum.HAM_1.getValue()) {
                        piecesAndButtons.remove(piecesAndButtons.size() - 1);
                        data.remove(data.size() - 1);
                    }
                }
            }
        }
        return data;
    }

    private static cBuilderManager ourInstance = new cBuilderManager();

    public static cBuilderManager getInstance() {
        return ourInstance;
    }

    private cBuilderManager() {
    }
}
