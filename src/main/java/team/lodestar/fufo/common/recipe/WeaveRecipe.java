package team.lodestar.fufo.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import team.lodestar.fufo.FufoMod;
import team.lodestar.fufo.core.weaving.Bindable;
import team.lodestar.fufo.core.weaving.BindingType;
import team.lodestar.fufo.core.weaving.Weave;
import team.lodestar.fufo.core.weaving.recipe.EntityTypeBindable;
import team.lodestar.fufo.core.weaving.recipe.IngredientBindable;
import team.lodestar.fufo.registry.common.FufoRecipeTypes;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an array
 */
public class WeaveRecipe extends Weave<WeaveRecipe> implements Recipe<Container> {

    @Deprecated
    public boolean matches(Container inv, Level level) {
        return false;
    }

    @Deprecated
    public ItemStack assemble(Container inv) {
        return ItemStack.EMPTY;
    }

    @Deprecated
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Deprecated
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static final String NAME = "weave";
    public static class Type implements RecipeType<WeaveRecipe> {
        @Override
        public String toString () {
            return FufoMod.FUFO + ":" + NAME;
        }

        public static final WeaveRecipe.Type INSTANCE = new WeaveRecipe.Type();
    }

    private final ResourceLocation id;

    /**
     * Map of resources to the amount of primers allowed
     */
    public final HashMap<ResourceLocation, Integer> primersAllowed = new HashMap<>();

    /**
     * Output of this weave
     */
    private ItemStack output;

    /**
     * Sets the output of this weave
     * @param output The output of this weave
     */
    public WeaveRecipe setOutput(ItemStack output) {
        this.output = output;
        return this;
    }

    /**
     * Gets the output of this weave
     * @return The output of this weave
     */
    public ItemStack getOutput() {
        return output;
    }

    /**
     * The type of this weave
     */
    private final String weaveType;

    public WeaveRecipe primersAllowed(ResourceLocation resourceLocation, int i) {
        primersAllowed.put(resourceLocation, i);
        return this;
    }

    public WeaveRecipe(Bindable base, ResourceLocation id, String weaveType)
    {
        super(base);
        this.id = id;
        this.weaveType = weaveType;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FufoRecipeTypes.WEAVE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Serializer implements RecipeSerializer<WeaveRecipe> {

        public static Vec3i readVec3i(JsonObject json) {
            return new Vec3i(json.get("x").getAsInt(), json.get("y").getAsInt(), json.get("z").getAsInt());
        }

        @Override
        public WeaveRecipe fromJson(ResourceLocation recipeId, JsonObject json)
        {
            // TODO: Add error messages here that are actually helpful
            String weaveType = json.get("weaveType").getAsString();
            JsonArray ingredients = json.getAsJsonObject("input").getAsJsonArray("ingredients");
            JsonObject primers = json.getAsJsonObject("input").getAsJsonObject("primers");
            JsonArray bindings = json.getAsJsonArray("bindings");

            WeaveRecipe weave = null;

            for (JsonElement ingredient : ingredients) {
                JsonObject jsonIngredient = ingredient.getAsJsonObject();
                String type = jsonIngredient.get("type").getAsString();

                Vec3i position = readVec3i(jsonIngredient.getAsJsonObject("position"));
                Vec3i size = readVec3i(jsonIngredient.getAsJsonObject("size"));

                Bindable bindable = null;
                if (type.equals("ingredient")) {
                    Ingredient value = Ingredient.fromJson(jsonIngredient.getAsJsonObject("value"));
                    bindable = new IngredientBindable(size, value);
                } else if (type.equals("entity")) {
                    EntityType<?> entityType = Registry.ENTITY_TYPE.get(new ResourceLocation(jsonIngredient.getAsJsonObject("value").get("entity").getAsString()));
                    bindable = new EntityTypeBindable(size, entityType);
                }

                if(weave == null) {
                    weave = new WeaveRecipe(bindable, recipeId, weaveType);
                } else {
                    weave.add(position, bindable);
                }
            }

            for (Map.Entry<String, JsonElement> entry : primers.entrySet()) {
                ResourceLocation resource = new ResourceLocation(entry.getKey());
                int amount = entry.getValue().getAsInt();
                weave.primersAllowed.put(resource, amount);
            }

            for (JsonElement binding : bindings) {
                JsonObject bindingObject = binding.getAsJsonObject();
                Vec3i start = readVec3i(bindingObject.getAsJsonObject("start"));
                Vec3i end = readVec3i(bindingObject.getAsJsonObject("end"));

                weave.link(start, end, new BindingType(new ResourceLocation(bindingObject.get("type").getAsString())));
            }

            Ingredient output = Ingredient.fromJson(json.getAsJsonObject("output"));
            weave.setOutput(output.getItems()[0]);

            return weave;
        }

        public void toJson(JsonObject json, WeaveRecipe recipe) {
            json.addProperty("type", "fufo:weave");
            json.addProperty("weaveType", recipe.weaveType);

            JsonObject input = new JsonObject();
            JsonArray ingredients = new JsonArray();
            JsonObject primers = new JsonObject();
            JsonArray bindings = new JsonArray();

            for (Bindable entry : recipe.getBindables()) {
                JsonObject ingredient = new JsonObject();
                if (entry instanceof IngredientBindable) {
                    ingredient.addProperty("type", "ingredient");
                    ingredient.add("value", ((IngredientBindable) entry).getIngredient().toJson());
                }
                if (entry instanceof EntityTypeBindable) {
                    ingredient.addProperty("type", "entity");
                    String name = ((EntityTypeBindable) entry).get().toString();
                    JsonObject value = new JsonObject();
                    value.addProperty("entity", name);
                    ingredient.add("value", value);
                }

                JsonObject position = new JsonObject();
                position.addProperty("x", entry.getLocation().getX());
                position.addProperty("y", entry.getLocation().getY());
                position.addProperty("z", entry.getLocation().getZ());

                ingredient.add("position", position);

                JsonObject size = new JsonObject();
                size.addProperty("x", entry.size().getX());
                size.addProperty("y", entry.size().getY());
                size.addProperty("z", entry.size().getZ());

                ingredient.add("size", size);

                ingredients.add(ingredient);
            }

            for (Map.Entry<ResourceLocation, Integer> entry : recipe.primersAllowed.entrySet()) {
                primers.addProperty(entry.getKey().toString(), entry.getValue());
            }

            for (Map.Entry<Pair<Vec3i, Vec3i>, BindingType> entry : recipe.getLinks().entrySet()) {
                JsonObject bindingObject = new JsonObject();
                bindingObject.addProperty("type", entry.getValue().id.toString());

                JsonObject start = new JsonObject();
                start.addProperty("x", entry.getKey().getFirst().getX());
                start.addProperty("y", entry.getKey().getFirst().getY());
                start.addProperty("z", entry.getKey().getFirst().getZ());


                JsonObject end = new JsonObject();
                end.addProperty("x", entry.getKey().getSecond().getX());
                end.addProperty("y", entry.getKey().getSecond().getY());
                end.addProperty("z", entry.getKey().getSecond().getZ());

                bindingObject.add("start", start);
                bindingObject.add("end", end);

                bindings.add(bindingObject);
            }

            input.add("ingredients", ingredients);
            input.add("primers", primers);
            json.add("input", input);
            json.add("bindings", bindings);
            json.add("output", Ingredient.of(recipe.getOutput()).toJson());
        }

        public WeaveRecipe fromNBT(CompoundTag nbt)
        {
            return fromJson(FufoMod.fufoPath("mock"), (JsonObject) Dynamic.convert(NbtOps.INSTANCE, JsonOps.INSTANCE, nbt));
        }

        public CompoundTag toNBT(WeaveRecipe recipe)
        {
            JsonObject json = new JsonObject();
            toJson(json, recipe);
            return (CompoundTag) Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, json);
        }

        @Nullable
        @Override
        public WeaveRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            WeaveRecipe weave = null;

            String weaveType = buffer.readUtf();
            int ingredientCount = buffer.readInt();
            for (int i = 0; i < ingredientCount; i++) {
                Vec3i position = new Vec3i(buffer.readInt(), buffer.readInt(), buffer.readInt());
                Vec3i size = new Vec3i(buffer.readInt(), buffer.readInt(), buffer.readInt());
                Bindable bindable = null;
                String type = buffer.readUtf();
                if (type.equals("ingredient")) {
                    Ingredient value = Ingredient.fromNetwork(buffer);
                    bindable = new IngredientBindable(size, value);
                }
                else if (type.equals("entity")) {
                    EntityType<?> entityType = Registry.ENTITY_TYPE.get(new ResourceLocation(buffer.readUtf()));
                    bindable = new EntityTypeBindable(size, entityType);
                }

                if(weave == null) {
                    weave = new WeaveRecipe(bindable, recipeId, weaveType);
                } else {
                    weave.add(position, bindable);
                }
            }

            int primerCount = buffer.readInt();
            for (int i = 0; i < primerCount; i++) {
                ResourceLocation resourceLocation = buffer.readResourceLocation();
                int amount = buffer.readInt();
                weave.primersAllowed.put(resourceLocation, amount);
            }

            int bindingCount = buffer.readInt();
            for (int i = 0; i < bindingCount; i++) {
                Vec3i start = new Vec3i(buffer.readInt(), buffer.readInt(), buffer.readInt());
                Vec3i end = new Vec3i(buffer.readInt(), buffer.readInt(), buffer.readInt());
                BindingType bindingType = new BindingType(buffer.readResourceLocation());
                weave.link(start, end, bindingType);
            }

            // output
            ItemStack output = buffer.readItem();

            weave.setOutput(output);

            return weave;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, WeaveRecipe recipe)
        {
            buffer.writeUtf(recipe.weaveType);
            buffer.writeInt(recipe.size());
            for (Bindable entry : recipe.getBindables()) {
                Vec3i position = entry.getLocation();
                Vec3i size = entry.size();
                buffer.writeInt(position.getX());
                buffer.writeInt(position.getY());
                buffer.writeInt(position.getZ());
                buffer.writeInt(size.getX());
                buffer.writeInt(size.getY());
                buffer.writeInt(size.getZ());
                if (entry instanceof IngredientBindable) {
                    buffer.writeUtf("ingredient");
                    IngredientBindable ingredientBindable = (IngredientBindable) entry;
                    Ingredient ingredient = ingredientBindable.getIngredient();
                    ingredient.toNetwork(buffer);
                } else if (entry instanceof EntityTypeBindable) {
                    buffer.writeUtf("entity");
                    EntityTypeBindable entityTypeBindable = (EntityTypeBindable) entry;
                    EntityType<?> entityType = entityTypeBindable.get();
                    buffer.writeUtf(ForgeRegistries.ENTITY_TYPES.getKey(entityType).toString());
                }
            }

            buffer.writeInt(recipe.primersAllowed.size());
            for (Map.Entry<ResourceLocation, Integer> entry : recipe.primersAllowed.entrySet()) {
                buffer.writeResourceLocation(entry.getKey());
                buffer.writeInt(entry.getValue());
            }

            buffer.writeInt(recipe.getLinks().size());

            for (Map.Entry<Pair<Vec3i, Vec3i>, BindingType> entry : recipe.getLinks().entrySet()) {
                buffer.writeInt(entry.getKey().getFirst().getX());
                buffer.writeInt(entry.getKey().getFirst().getY());
                buffer.writeInt(entry.getKey().getFirst().getZ());
                buffer.writeInt(entry.getKey().getSecond().getX());
                buffer.writeInt(entry.getKey().getSecond().getY());
                buffer.writeInt(entry.getKey().getSecond().getZ());
                buffer.writeResourceLocation(entry.getValue().id);
            }

            buffer.writeItem(recipe.getOutput());
        }
    }
}
