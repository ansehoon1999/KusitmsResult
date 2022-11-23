package com.example.kusitmsresult.Second;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainTabPagerAdapter extends FragmentStatePagerAdapter {
  private List<Fragment> fragmentList = new ArrayList<>();

  public MainTabPagerAdapter(FragmentManager fragmentManager) {
    super (fragmentManager);
  }

  @Override
  public int getCount() {
    return fragmentList.size ();
  }

  @Override
  public Fragment getItem(int position) {
    return fragmentList.get (position);
  }

  public void setFragmentList(List<Fragment> fragmentList) {
    this.fragmentList = fragmentList;
    notifyDataSetChanged ();
  }

  public void addFragment(Fragment fragment) {
    fragmentList.add (fragment);
    notifyDataSetChanged ();
  }
}