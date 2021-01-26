package superworldsun.superslegend.entities.mobs.fairy;

import com.google.common.cache.RemovalNotification;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.tags.ITag;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.lists.ItemList;
import superworldsun.superslegend.registries.EntityInit;

import javax.annotation.Nullable;
import java.util.EnumSet;


public class FairyEntity extends AnimalEntity implements IFlyingAnimal {


    public FairyEntity(EntityType<? extends AnimalEntity> type, World world) {
        super(type,world);
        this.moveController = new FlyingMovementController(this, 20, true);
    }

    public int getMaxSpawnedInChunk() {
        return 10;
    }

    public boolean isMaxGroupSize(int sizeIn) {
        return false;
    }

    public static AttributeModifierMap.MutableAttribute prepareAttributes() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 20.0D)
                .createMutableAttribute(Attributes.FLYING_SPEED, 0.8F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 10.0F, 0.5D, 0.5D));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 10.0F, 0.2F));
        this.goalSelector.addGoal(3, new RandomWalkingGoal(this, 0.6d));
        this.goalSelector.addGoal(5, new WanderGoal());
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_BAT_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_BAT_HURT;
    }

    protected boolean isDespawnPeaceful() {
        return false;
    }

    public ActionResultType func_230254_b_(PlayerEntity p_230254_1_, Hand p_230254_2_) {
        ItemStack itemstack = p_230254_1_.getHeldItem(p_230254_2_);
        if (itemstack.getItem() == Items.GLASS_BOTTLE) {
            p_230254_1_.playSound(SoundEvents.ITEM_BOTTLE_FILL, 1.0F, 1.0F);
            ItemStack itemstack1 = DrinkHelper.fill(itemstack, p_230254_1_, ItemList.fairy_bottle.getDefaultInstance());
            p_230254_1_.setHeldItem(p_230254_2_, itemstack1);
            this.remove();
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else {
            return super.func_230254_b_(p_230254_1_, p_230254_2_);
        }
    }

    protected PathNavigator createNavigator(World worldIn) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn) {
            public boolean canEntityStandOnPos(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanSwim(false);
        flyingpathnavigator.setCanEnterDoors(true);
        return flyingpathnavigator;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.isChild() ? sizeIn.height * 0.5F : sizeIn.height * 0.5F;
    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    protected boolean makeFlySound() {
        return true;
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    protected void handleFluidJump(ITag<Fluid> fluidTag) {
        this.setMotion(this.getMotion().add(0.0D, 0.5D, 0.0D));
    }


    public void livingTick() {
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(1.0D, 1.0D, 1.0D));
        }

        if (this.inWater) {
            this.setMotion(this.getMotion().mul(1.0D, 1.0D, 1.0D));
        }

        if (this.world.isRemote) {
            if (this.rand.nextInt(28) == 0 && !this.isSilent()) {
                this.world.playSound(this.getPosX() + 0.5D, this.getPosY() + 0.5D, this.getPosZ() + 0.5D, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, this.getSoundCategory(), 0.2F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
                this.world.addParticle(ParticleTypes.POOF, this.getPosXRandom(0.1D), this.getPosYRandom(), this.getPosZRandom(0.1D), 0.0D, 0.0D, 0.0D);
            }


        }

        super.livingTick();
    }

    public void baseTick() {
        super.baseTick();
        this.world.getProfiler().startSection("mobBaseTick");
        if (this.isAlive() && this.rand.nextInt(60) < this.livingSoundTime++) {
            this.func_241821_eG();
            this.playAmbientSound();
        }

        this.world.getProfiler().endSection();
    }

    protected void playHurtSound(DamageSource source) {
        this.func_241821_eG();
        super.playHurtSound(source);
    }

    private void func_241821_eG() {
        this.livingSoundTime = -this.getTalkInterval();
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d func_241205_ce_() {
        return new Vector3d(0.0D, 0.5F * this.getEyeHeight(), this.getWidth() * 0.2F);
    }

    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    class WanderGoal extends Goal {
        WanderGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return FairyEntity.this.navigator.noPath() && FairyEntity.this.rand.nextInt(3) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return FairyEntity.this.navigator.hasPath();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            Vector3d vector3d = this.getRandomLocation();
            if (vector3d != null) {
                FairyEntity.this.navigator.setPath(FairyEntity.this.navigator.getPathToPos(new BlockPos(vector3d), 1), 1.0D);
            }

        }

        @Nullable
        private Vector3d getRandomLocation() {
            Vector3d vector3d = FairyEntity.this.getLook(0.0F);
            int i = 8;
            Vector3d vector3d2 = RandomPositionGenerator.findAirTarget(FairyEntity.this, 3, 3, vector3d, ((float) Math.PI / 2F), 1, 1);
            return vector3d2 != null ? vector3d2 : RandomPositionGenerator.findGroundTarget(FairyEntity.this, 3, 3, -3, vector3d, (float) Math.PI / 2F);
        }
    }


}
