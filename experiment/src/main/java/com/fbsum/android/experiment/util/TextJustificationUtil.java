package com.fbsum.android.experiment.util;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by twiceYuan on 2017/5/6.
 * <p>
 * 使用 Spannable 让 TextView 文字两端对齐。
 * <p>
 * 原理是测量原始行中空格占用的宽度总和，让行尾的空白平均到所有空格中去。使用 Span 来代替空格保证空格的宽度不受字体影响。
 */
public class TextJustificationUtil {

    public static Spannable justify(final Layout layout) {
        if (TextUtils.isEmpty((layout.getText()))) {
            return null;
        }

        // 用于测量文字宽度，计算分散对齐后的空格宽度
        final TextPaint textPaint = layout.getPaint();
        // 处理前原始字符串
        final String originalTextString = layout.getText().toString();

        CharSequence textViewText = layout.getText();
        // 分散对齐后的文字
        final Spannable spannable = textViewText instanceof Spannable
                ? (Spannable) textViewText : new SpannableString(originalTextString);

        // 获取 textView 的宽度
        final int textViewWidth = layout.getWidth();
        // 遍历行，最后一行不做处理
        for (int i = 0, lineCount = layout.getLineCount(); i < lineCount - 1; i++) {
            // 获取行首字符位置和行尾字符位置来截取每行的文字
            int lineStart = layout.getLineStart(i);
            int lineEnd = layout.getLineEnd(i);
            // 获取行字符串
            String lineString = originalTextString.substring(lineStart, lineEnd);

            // 存在"回车"的行不需要对齐
            if(lineString.endsWith("\n")){
                continue;
            }

            // 行首行尾去掉空格以保证处理后的对齐效果
            String trimSpaceText = lineString.trim();
            String removeSpaceText = lineString.replaceAll(" ", "");

            float removeSpaceWidth = textPaint.measureText(removeSpaceText);
            float spaceCount = trimSpaceText.length() - removeSpaceText.length();

            // 两端对齐时每个空格的重新计算的宽度
            float eachSpaceWidth = (textViewWidth - removeSpaceWidth) / spaceCount;

            // 两端空格需要单独处理
            Set<Integer> endsSpace = spacePositionInEnds(lineString);
            char c;
            Drawable zeroWidthDrawable = null;
            Drawable insertDrawable = null;
            ImageSpan span = null;
            for (int j = 0, lineStringLength = lineString.length(); j < lineStringLength; j++) {
                c = lineString.charAt(j);
                if (c == ' ') {// 使用透明的 drawable 来填充空格部分
                    if (endsSpace.contains(j)) {// 如果是两端的空格，则宽度置为 0
                        if (zeroWidthDrawable == null) {
                            zeroWidthDrawable = new ColorDrawable(0x110000);
                            zeroWidthDrawable.setBounds(0, 0, 0, 0);
                        }
                        span = new ImageSpan(zeroWidthDrawable);
                    } else {
                        if (insertDrawable == null) {
                            insertDrawable = new ColorDrawable(0x110000);
                            insertDrawable.setBounds(0, 0, (int) eachSpaceWidth, 0);
                        }
                        span = new ImageSpan(insertDrawable);
                    }
                    spannable.setSpan(span, lineStart + j, lineStart + j + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }

        return spannable;
    }

    /**
     * 返回两端的空格坐标，例如字符串 " abc  "（前面一个空格，后面两个空格）就返回 [0, 4, 5]
     */
    private static Set<Integer> spacePositionInEnds(String string) {
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == ' ') {
                result.add(i);
            } else {
                break;
            }
        }

        if (result.size() == string.length()) {
            return result;
        }

        for (int i = string.length() - 1; i > 0; i--) {
            char c = string.charAt(i);
            if (c == ' ') {
                result.add(i);
            } else {
                break;
            }
        }

        return result;
    }
}