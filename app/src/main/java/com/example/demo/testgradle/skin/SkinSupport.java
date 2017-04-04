package com.example.demo.testgradle.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.example.demo.testgradle.util.LoggerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guilianghuang on 2017/4/4.
 */

public class SkinSupport {

    /**
     *
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getSkinView(Context context, AttributeSet attrs) {
        List<SkinAttr> skinAttrs = new ArrayList<>();

        int attributeCount = attrs.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attributeName = attrs.getAttributeName(i);

            String attributeValue = attrs.getAttributeValue(i);

            LoggerUtils.debug("attributeName==="+attributeName+"    attributeValue  =="+ attributeValue);

            SkinType skinType = getSkinType(attributeName);

            if (skinType != null) {


                String resName = getResName(context, attributeValue);

                if (TextUtils.isEmpty(resName)) {
                    continue;
                }
                SkinAttr skinAttr=new SkinAttr(resName,skinType);

                skinAttrs.add(skinAttr);
            }

        }

        return skinAttrs;
    }

    private static String getResName(Context context, String attrValue) {

        if (attrValue.startsWith("@")) {
            attrValue=   attrValue.substring(1, attrValue.length());
            int id = Integer.parseInt(attrValue);
            return  context.getResources().getResourceEntryName(id);
        }


        return null;
    }

    private static SkinType getSkinType(String attrsName) {

        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {

            if (skinType.getResName().equals(attrsName)) {

                return skinType;
            }
        }

        return null;
    }
}
