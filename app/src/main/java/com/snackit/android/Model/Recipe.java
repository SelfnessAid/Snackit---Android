package com.snackit.android.Model;

import com.contentful.vault.Asset;
import com.contentful.vault.ContentType;
import com.contentful.vault.Field;
import com.contentful.vault.Resource;

import java.util.List;

@ContentType("recipe")
public final class Recipe extends Resource {
    @Field String title;
    @Field Asset image;
    @Field List<Ingredient> ingredients;
    @Field String preparation;
    @Field List<String> tags;
}
