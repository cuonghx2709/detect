package com.gemvietnam.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gemvietnam.base.log.Logger;
import com.gemvietnam.common.R;
import com.gemvietnam.eventbus.EventBusWrapper;
import com.gemvietnam.utils.ActivityUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Base Fragment
 * Created by neo on 3/22/2016.
 */
public abstract class BaseFragment extends Fragment {
  private static final boolean DEFAULT_START_ON_ANIMATION_ENDED = false;

  protected View mRootView;
  protected View mReloadView;
  protected View mContentView;

  // Animation enter/exit
  protected int mAnimIn = CoreDefault.ANIM_NONE;
  protected int mAnimOut = CoreDefault.ANIM_NONE;

  /**
   * This field decide the Fragment will be started fetching data after Fragment transaction
   * animation ended
   * Otherwise, it start {@code onActivityCreated}
   */
  protected boolean mStartOnAnimationEnded = DEFAULT_START_ON_ANIMATION_ENDED;

  protected boolean mIsStarted = false;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    mRootView = inflater.inflate(getLayoutId(), container, false);

    if (shouldListenEventBus()) {
      if (!EventBusWrapper.isRegistered(this)) {
        EventBusWrapper.register(this);
      }
    }

    // Inject views
    ButterKnife.bind(this, mRootView);
    mRootView.setClickable(true);
    mRootView.setFocusable(true);
    mRootView.setFocusableInTouchMode(true);
    return mRootView;
  }

  public void addChildFragment(Fragment fragment, int frameId, boolean addToBackStack,
                               String tag) {
    ActivityUtils.addChildFragment(getChildFragmentManager(), fragment,
        frameId, addToBackStack, tag);
  }

  /**
   * Set out animation
   */
  public BaseFragment setAnimOut(int animOut) {
    mAnimOut = animOut;
    return this;
  }

  /**
   * Set enter animation
   */
  public BaseFragment setAnimIn(int animIn) {
    mAnimIn = animIn;
    return this;
  }

  /**
   * This method decide the Fragment will be started fetching data after Fragment transaction
   * animation ended
   * Otherwise, it start {@code onActivityCreated}
   */
  protected BaseFragment setStartOnAnimationEnded(boolean startOnAnimationEnded) {
    mStartOnAnimationEnded = startOnAnimationEnded;
    return this;
  }

  /**
   * Return layout resource id for activity
   */
  protected abstract int getLayoutId();

  /**
   * Start present fragment
   */
  protected abstract void startPresent();

  // Arbitrary value; set it to some reasonable default
//    private static final int DEFAULT_CHILD_ANIMATION_DURATION = 250;
//
//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        final Fragment parent = getParentFragment();
//
//        // Apply the workaround only if this is a child fragment, and the parent
//        // is being removed.
//        if (!enter && parent != null && parent.isRemoving()) {
//            // This is a workaround for the bug where child fragments disappear when
//            // the parent is removed (as all children are first removed from the parent)
//            // See https://code.google.com/p/android/issues/detail?id=55228
//            Animation doNothingAnim = new AlphaAnimation(1, 1);
//            doNothingAnim.setDuration(getNextAnimationDuration(parent,
// DEFAULT_CHILD_ANIMATION_DURATION));
//            return doNothingAnim;
//        } else {
//            return super.onCreateAnimation(transit, enter, nextAnim);
//        }
//    }

  //    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        return enter ? AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_in) :
// AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_out);
//    }
  private static long getNextAnimationDuration(Fragment fragment, long defValue) {
    try {
      // Attempt to get the resource ID of the next animation that
      // will be applied to the given fragment.
      Field nextAnimField = Fragment.class.getDeclaredField("mNextAnim");
      nextAnimField.setAccessible(true);
      int nextAnimResource = nextAnimField.getInt(fragment);
      Animation nextAnim = AnimationUtils.loadAnimation(fragment.getActivity(), nextAnimResource);

      // ...and if it can be loaded, return that animation's duration
      return (nextAnim == null) ? defValue : nextAnim.getDuration();
    } catch (NoSuchFieldException | IllegalAccessException | Resources.NotFoundException ex) {
      Logger.e(ex);
      return defValue;
    }
  }

  protected abstract boolean needTranslationAnimation();

  @Override
  public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
    if (!needTranslationAnimation()) {
      return null;
    }

    Animation anim;
    if (enter) {
      anim = AnimationUtils.loadAnimation(getActivity(), mAnimIn);
    } else {
      anim = AnimationUtils.loadAnimation(getActivity(), mAnimOut);
    }

    anim.setAnimationListener(new Animation.AnimationListener() {
      @Override
      public void onAnimationEnd(Animation animation) {
        if (mRootView != null) {
          mRootView.setLayerType(View.LAYER_TYPE_NONE, null);
        }

        if (mStartOnAnimationEnded && !mIsStarted) {
          startPresent();
        }
      }

      @Override
      public void onAnimationRepeat(Animation animation) {
      }

      @Override
      public void onAnimationStart(Animation animation) {

      }
    });

    // Apply the workaround only if this is a child fragment, and the parent
    // is being removed.
    final Fragment parent = getParentFragment();
    if (!enter && parent != null && parent.isRemoving()) {
      Animation doNothingAnim = new AlphaAnimation(1, 1);
      doNothingAnim.setDuration(getNextAnimationDuration(parent, getContext().getResources()
          .getInteger(R.integer.anim_duration)));
      return doNothingAnim;
    }
    return anim;
  }

  //[START] bavv add to set event bus in fragment
  protected boolean shouldListenEventBus() {
    return false;
  }

  protected void onReloadData() {

  }

  protected void initReloadView() {
    mReloadView = mRootView.findViewById(R.id.layout_reload);
    mContentView = mRootView.findViewById(R.id.layout_content);
  }

  protected void showReloadView() {
    if (null != mReloadView) {
      mReloadView.setVisibility(View.VISIBLE);
      mReloadView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          hideReloadView();
          onReloadData();
        }
      });
    }
    if (null != mContentView) {
      mContentView.setVisibility(View.GONE);
    }

  }

  protected void hideReloadView() {
    if (null != mReloadView) {
      mReloadView.setVisibility(View.GONE);
    }
    if (null != mContentView) {
      mContentView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (EventBusWrapper.isRegistered(this)) {
      EventBusWrapper.unregister(this);
    }
  }
  //[END] bavv add to set event bus in fragment
}
