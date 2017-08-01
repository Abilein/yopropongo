package com.xiberty.propongo.councils.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.pchmn.materialchips.model.ChipInterface;
import com.xiberty.propongo.councils.NewProposalActivity;
import com.xiberty.propongo.database.CouncilMan;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by growcallisaya on 21/7/17.
 */

public class CouncilManChip implements ChipInterface{


    CouncilMan councilman;
    Context context;
    Bitmap bitmap;




    public CouncilManChip(Context c, CouncilMan councilMan) {
        this.councilman = councilMan;
        this.context = c;
        Glide.with(c)
                .load(councilMan.getAvatar())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        bitmap= resource;
                    }
                });
    }

    @Override
    public Object getId() {
        return null;
    }

    @Override
    public Uri getAvatarUri() {
        Uri uri = getImageUri(context,bitmap);
        return uri;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
    }

    @Override
    public Drawable getAvatarDrawable() {
        return null;
    }

    @Override
    public String getLabel() {
        return councilman.getFullName();
    }

    @Override
    public String getInfo() {
        return councilman.getAgrupation();
    }
}
