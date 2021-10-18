package com.noname81.lmt;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import java.util.Vector;

class PieControl extends PieControlBase implements View.OnClickListener, View.OnLongClickListener, View.OnKeyListener {
    private Launcher mLauncher;
    private Vector<PieItemAction> mPieItemActions = new Vector<>();
    private SettingsValues mSettings;

    class PieItemAction {
        Action mClickAction;
        Action mLongClickAction;
        PieItem mPieItem;
        boolean mTempAction;

        PieItemAction(PieItem pieItem, Action clickAction, Action longClickAction, boolean tempAction) {
            this.mPieItem = pieItem;
            this.mClickAction = clickAction;
            this.mLongClickAction = longClickAction;
            this.mTempAction = tempAction;
        }
    }

    PieControl(Context context) {
        super(context);
        this.mLauncher = Launcher.getInstance(context);
        this.mSettings = SettingsValues.getInstance(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.noname81.lmt.PieControlBase
    public void populateMenu() {
        int userImageScaling = this.mSettings.loadPieShowScaleUserImages();
        int showAppImages = this.mSettings.loadPieShowScaleAppImages();
        for (int i = 0; i < 9; i += 2) {
            if (this.mSettings.getPieAction(i).getType() > 1) {
                this.mPieItemActions.add(new PieItemAction(makeItem(this.mSettings.getPieAction(i).getDrawable(this.mContext, IconUtils.getNamePie(i), userImageScaling, showAppImages, false), this.mSettings.getPieAction(i + 1).getDrawable(this.mContext, IconUtils.getNamePie(i + 1), userImageScaling, showAppImages, false), 1), this.mSettings.getPieAction(i), this.mSettings.getPieAction(i + 1), false));
            }
        }
        for (int i2 = 10; i2 < 23; i2 += 2) {
            if (this.mSettings.getPieAction(i2).getType() > 1) {
                this.mPieItemActions.add(new PieItemAction(makeItem(this.mSettings.getPieAction(i2).getDrawable(this.mContext, IconUtils.getNamePie(i2), userImageScaling, showAppImages, false), this.mSettings.getPieAction(i2 + 1).getDrawable(this.mContext, IconUtils.getNamePie(i2 + 1), userImageScaling, showAppImages, false), 2), this.mSettings.getPieAction(i2), this.mSettings.getPieAction(i2 + 1), false));
            }
        }
        if (this.mPieItemActions.size() == 0) {
            createDefaultPieActions();
        }
        for (int i3 = 0; i3 < this.mPieItemActions.size(); i3++) {
            this.mPie.addItem(this.mPieItemActions.get(i3).mPieItem);
            setClickListener(this, this.mPieItemActions.get(i3).mPieItem);
            setLongClickListener(this, this.mPieItemActions.get(i3).mPieItem);
            setKeyListener(this, this.mPieItemActions.get(i3).mPieItem);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v7, resolved type: java.util.Vector<com.noname81.lmt.PieControl$PieItemAction> */
    /* JADX WARN: Multi-variable type inference failed */
    private void activatePieRecentApps() {
        int userImageScaling = this.mSettings.loadPieShowScaleUserImages();
        int showAppImages = this.mSettings.loadPieShowScaleAppImages();
        String packageNames = this.mSettings.getPackageNamesOfRecentApps(12);
        if (packageNames.length() > 0) {
            String[] packageNamesArray = packageNames.split(" ");
            Vector<PieItemAction> pieItemActionsForRecents = new Vector<>();
            int i = 0;
            while (true) {
                int i2 = 1;
                if (i >= packageNamesArray.length) {
                    break;
                }
                Action asp = new Action(2, packageNamesArray[i]);
                Action alp = new Action(1, BuildConfig.FLAVOR);
                Drawable drawable = asp.getDrawable(this.mContext, null, userImageScaling, showAppImages, false);
                Drawable drawable2 = alp.getDrawable(this.mContext, null, userImageScaling, showAppImages, false);
                if (i > 4) {
                    i2 = 2;
                }
                pieItemActionsForRecents.add(new PieItemAction(makeItem(drawable, drawable2, i2), asp, alp, true));
                i++;
                pieItemActionsForRecents = pieItemActionsForRecents;
                packageNamesArray = packageNamesArray;
                packageNames = packageNames;
            }
            this.mPie.clearItems();
            for (int i3 = 0; i3 < pieItemActionsForRecents.size(); i3++) {
                this.mPie.addItem(pieItemActionsForRecents.get(i3).mPieItem);
                setClickListener(this, pieItemActionsForRecents.get(i3).mPieItem);
                setLongClickListener(this, pieItemActionsForRecents.get(i3).mPieItem);
                setKeyListener(this, pieItemActionsForRecents.get(i3).mPieItem);
                this.mPieItemActions.add(pieItemActionsForRecents.get(i3));
            }
            this.mPie.relayoutPie();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.noname81.lmt.PieControlBase
    public void attachToContainer(FrameLayout container) {
        boolean tempActionsAvailable = false;
        int i = 0;
        while (i < this.mPieItemActions.size()) {
            if (this.mPieItemActions.get(i).mTempAction) {
                this.mPieItemActions.remove(i);
                if (i > 0) {
                    i--;
                }
                tempActionsAvailable = true;
            }
            i++;
        }
        if (tempActionsAvailable) {
            this.mPie.clearItems();
            for (int i2 = 0; i2 < this.mPieItemActions.size(); i2++) {
                this.mPie.addItem(this.mPieItemActions.get(i2).mPieItem);
            }
        }
        super.attachToContainer(container);
    }

    public void onClick(View v) {
        for (int i = 0; i < this.mPieItemActions.size(); i++) {
            if (this.mPieItemActions.get(i).mPieItem.getView() == v) {
                this.mLauncher.fireAction(this.mPieItemActions.get(i).mClickAction);
                return;
            }
        }
    }

    public boolean onLongClick(View v) {
        int i = 0;
        while (true) {
            if (i >= this.mPieItemActions.size()) {
                break;
            } else if (this.mPieItemActions.get(i).mPieItem.getView() != v) {
                i++;
            } else if (this.mPieItemActions.get(i).mLongClickAction.getType() != 1) {
                this.mLauncher.fireAction(this.mPieItemActions.get(i).mLongClickAction);
            } else {
                this.mLauncher.fireAction(this.mPieItemActions.get(i).mClickAction);
            }
        }
        return true;
    }

    public boolean onKey(View v, int key, KeyEvent keyEvent) {
        for (int i = 0; i < this.mPieItemActions.size(); i++) {
            if (this.mPieItemActions.get(i).mPieItem.getView() == v) {
                if (key == 36) {
                    if (this.mPieItemActions.get(i).mClickAction.getType() == 44) {
                        this.mPie.activatePiePointer();
                    } else if (this.mPieItemActions.get(i).mClickAction.getType() == 45) {
                        activatePieRecentApps();
                    }
                } else if (key == 40) {
                    if (this.mPieItemActions.get(i).mLongClickAction.getType() == 44) {
                        this.mPie.activatePiePointer();
                    } else if (this.mPieItemActions.get(i).mLongClickAction.getType() == 45) {
                        activatePieRecentApps();
                    }
                }
            }
        }
        return true;
    }

    private void createDefaultPieActions() {
        int userImageScaling = this.mSettings.loadPieShowScaleUserImages();
        int showAppImages = this.mSettings.loadPieShowScaleAppImages();
        this.mPieItemActions.clear();
        Action a1sp = new Action(7);
        Action a1lp = new Action(30);
        Action a2sp = new Action(3);
        Action a2lp = new Action(26, "26");
        Action a3sp = new Action(9);
        Action a3lp = new Action(31);
        Action a4sp = new Action(5);
        Action a4lp = new Action(2, "com.android.settings");
        Action a5sp = new Action(10);
        Action a5lp = new Action(11);
        this.mPieItemActions.add(new PieItemAction(makeItem(a1sp.getDrawable(this.mContext, IconUtils.getNamePie(1), userImageScaling, showAppImages, false), a1lp.getDrawable(this.mContext, IconUtils.getNamePie(2), userImageScaling, showAppImages, false), 1), a1sp, a1lp, false));
        this.mPieItemActions.add(new PieItemAction(makeItem(a2sp.getDrawable(this.mContext, IconUtils.getNamePie(3), userImageScaling, showAppImages, false), a2lp.getDrawable(this.mContext, IconUtils.getNamePie(4), userImageScaling, showAppImages, false), 1), a2sp, a2lp, false));
        this.mPieItemActions.add(new PieItemAction(makeItem(a3sp.getDrawable(this.mContext, IconUtils.getNamePie(5), userImageScaling, showAppImages, false), a3lp.getDrawable(this.mContext, IconUtils.getNamePie(6), userImageScaling, showAppImages, false), 1), a3sp, a3lp, false));
        this.mPieItemActions.add(new PieItemAction(makeItem(a4sp.getDrawable(this.mContext, IconUtils.getNamePie(7), userImageScaling, showAppImages, false), a4lp.getDrawable(this.mContext, IconUtils.getNamePie(8), userImageScaling, showAppImages, false), 1), a4sp, a4lp, false));
        this.mPieItemActions.add(new PieItemAction(makeItem(a5sp.getDrawable(this.mContext, IconUtils.getNamePie(9), userImageScaling, showAppImages, false), a5lp.getDrawable(this.mContext, IconUtils.getNamePie(10), userImageScaling, showAppImages, false), 1), a5sp, a5lp, false));
    }
}