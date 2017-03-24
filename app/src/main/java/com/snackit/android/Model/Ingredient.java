package com.snackit.android.snackit.Model;

import com.contentful.vault.Asset;
import com.contentful.vault.ContentType;
import com.contentful.vault.Field;
import com.contentful.vault.Resource;

@ContentType("ingredient")
public final class Ingredient extends Resource {
    @Field String name;
    @Field String amount;
    @Field String unit;
    @Field String ingredient;
    @Field Asset image;
}
