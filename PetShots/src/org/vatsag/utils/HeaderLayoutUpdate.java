package org.vatsag.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.vatsag.R;

/**
 * <b>HeaderLayoutUpdate</b> Update Common Header View used by &lt include &gt method.
 * @author GCA4KOR
 *
 */
public class HeaderLayoutUpdate {
	private Activity activity;
	private int appWidth=0;
	private int appHeight=0;
	private SharedPrefsManager mPrefs;
	
	/**
	 * HeaderLayoutUpdate will update Header View of the given activity
	 * @param activity -Activity reference
	 * @param title -  Title String
	 * @param titleImage -  Title Image Res ID int
	 */
	public HeaderLayoutUpdate(Activity activity,String title,int titleImage) {

		this.activity=activity;
		mPrefs = new SharedPrefsManager(activity,Constants.UI_Header+Constants.UIUPDATE);
		 PhoneSpecifications.getPhoneSpecifications(activity);
		this.appHeight=PhoneSpecifications.getPhoneHeight();
		this.appWidth=PhoneSpecifications.getActualWidth();
		
		if(mPrefs.GetPrefs(Constants.UI_Header+Constants.UIUPDATE, "false").equals("true")){
			//updateViewsFromPrefs(title,titleImage);
//			updateLogoImageSize();
//			updateVehicleProfile();
			updateTitleBar(title,titleImage);
		}else{
//			updateLogoImageSize();
//			updateVehicleProfile();
			updateTitleBar(title,titleImage);
			mPrefs.EditPrefs(Constants.UI_Header+ Constants.UIUPDATE, "true"); //will be made true after updating actual UI update for once.
		}
	}

//	void updateVehicleProfile(){
//		TextView textView = (TextView)activity.findViewById(R.id.textview_pageinfo);
//		RelativeLayout.LayoutParams lauoutParamOld = (RelativeLayout.LayoutParams) textView.getLayoutParams();
//		int textview_vehicle_profile_PercentageWidth_leftMargin = 3;
//		lauoutParamOld.leftMargin=  (int) ((appWidth* textview_vehicle_profile_PercentageWidth_leftMargin)/100);
//		textView.setLayoutParams(lauoutParamOld);
//
//		float textview_vehicle_profile_PercentageHeigth_textSize= 2.4f;
//		float textview_vehicle_profile_TextSize_calculated = (appHeight* textview_vehicle_profile_PercentageHeigth_textSize)/100;
//		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textview_vehicle_profile_TextSize_calculated);
//	}

//	private void updateLogoImageSize() {
//		{
//			float percentageHeigth = 4.5f;//56/1230*100 -(56 in S3 of app heigth 1230)
//			ImageView imageView = (ImageView)activity.findViewById(R.id.settings_vatsag_logo);
//			RelativeLayout.LayoutParams layoutParamsTitle = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
//			int calculatedtitleHeigth = (int)(appHeight* percentageHeigth)/100;
//			layoutParamsTitle.height =calculatedtitleHeigth; 
//			imageView.setLayoutParams(layoutParamsTitle);
//			
//		}
//		
//		/*Image View screen_2_logo*/
//		{
//			ImageView imageView = (ImageView)activity.findViewById(R.id.settings_vatsag_logo);
//			RelativeLayout.LayoutParams layoutParamOldImageView = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
//			int screen_2_logo_PercentageWidth_layout_marginRight = 3;
//			layoutParamOldImageView.rightMargin=  (int) ((appWidth* screen_2_logo_PercentageWidth_layout_marginRight)/100);
//			imageView.setLayoutParams(layoutParamOldImageView);
//			float screen_2_logo_PercentageWidth_layout_topMargin = 1.3f;
//			layoutParamOldImageView.topMargin=  (int) ((appHeight* screen_2_logo_PercentageWidth_layout_topMargin)/100);
//			imageView.setLayoutParams(layoutParamOldImageView);
//			
//		}
//	}

	private void updateTitleBar(String title,int titleImage) {

		float percentageTopMargin = 5.5f;//60/1230*100 -(60 in S3 of app heigth 1230)
		RelativeLayout layout = (RelativeLayout)activity.findViewById(R.id.title_layout);
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
		int calculatedTopMargin =(int)(appHeight* percentageTopMargin)/100;
		layoutParams.topMargin=calculatedTopMargin-3;
		layout.setLayoutParams(layoutParams);
		mPrefs.EditPrefs(Constants.UI_Header+Utils.getResourceName(activity, R.id.title_layout)+"_Margin_t",""+layoutParams.topMargin);//(calculatedTopMargin-3));

		float percentageHeigth = 5.5f;//68/1230*100 -(60 in S3 of app heigth 1230)
		ImageView imageViewTitle = (ImageView)activity.findViewById(R.id.title_img);
		RelativeLayout.LayoutParams layoutParamsTitle = (RelativeLayout.LayoutParams) imageViewTitle .getLayoutParams();
		int calculatedtitleHeigth = (int)(appHeight* percentageHeigth)/100;
		layoutParamsTitle.height =calculatedtitleHeigth; 
		imageViewTitle.setLayoutParams(layoutParamsTitle);
		imageViewTitle.setImageResource(titleImage);
		mPrefs.EditPrefs(Constants.UI_Header+Utils.getResourceName(activity, R.id.title_img)+"_height",""+calculatedtitleHeigth);
		
		//Title bar Textview Layout Handling.This is based on underlying Image View trip_analyzer_title
		TextView imageViewTitleText = (TextView)activity.findViewById(R.id.title_text);
		RelativeLayout.LayoutParams layoutParamsTitleText = (RelativeLayout.LayoutParams) imageViewTitleText .getLayoutParams();
		layoutParamsTitleText.height =calculatedtitleHeigth;
		float percentageShiftUp = .11f;//This has to be based on the Titlebar heigth.8 for title bar heigth of 68 in S3, which is 11 percentage.
		layoutParamsTitleText.topMargin =(int) layoutParamsTitle.topMargin-(int)(calculatedtitleHeigth* percentageShiftUp); 
		layoutParamsTitleText.bottomMargin=(layoutParamsTitle.bottomMargin + (int)(calculatedtitleHeigth* percentageShiftUp));
		imageViewTitleText.setLayoutParams(layoutParamsTitleText);
		//Title bar Text Size Handling. 
		float percentageHeigthOfView= .70f;//80 percentage of the textView
		float textviewTextSizeCalculated = (calculatedtitleHeigth* percentageHeigthOfView);
		imageViewTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textviewTextSizeCalculated);
		imageViewTitleText.setTypeface(imageViewTitleText.getTypeface(), 1);
		imageViewTitleText.setText(title);  
		
	}

}


