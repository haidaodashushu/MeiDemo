package com.example.meidemo.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meidemo.R;
import com.example.meidemo.CommonUtils.ImageUtil;
import com.example.meidemo.CommonUtils.ImageUtil.ScalingLogic;
import com.example.meidemo.CommonUtils.ScreenUtil;
import com.example.meidemo.data.GlobalData;
import com.example.meidemo.view.entity.CategoryFood;
import com.example.meidemo.view.entity.CategoryVideo;
import com.example.meidemo.view.entity.Comment;
import com.example.meidemo.view.entity.Comments;
import com.example.meidemo.view.entity.DetailCategory;
import com.example.meidemo.view.entity.Service;
import com.example.meidemo.view.entity.Team;

public class DetailVideoCategoryActivity extends Activity implements
		OnClickListener {
	/**
	 * 团购ID
	 */
	String id;
	/**
	 * 当前展示的最大ID
	 */
	int commentId;
	DetailCategory detailCategory = null;// 解析团购详情数据
	/** 团购说明 */
	LinearLayout category_instruction;
	FinalBitmap fb;
	CategoryVideo categoryFood;

	LinearLayout category_food_infos;

	LinearLayout category_tips_infos;
	TextView tips_infos;
	ProgressBar progressBar;
	LinearLayout category_evaluation_infos;
	LinearLayout rest_evaluation;
	TextView rest_evaluation_text;

	TextView evaluation_count;

	LinearLayout category_evaluation;
	LinearLayout tipsLv;
	LinearLayout category_infos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_video_category);
		category_instruction = (LinearLayout) findViewById(R.id.category_instruction);
		category_tips_infos = (LinearLayout) findViewById(R.id.category_tips_infos);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		
		tipsLv = (LinearLayout)findViewById(R.id.tipsLv);
		category_infos = (LinearLayout)findViewById(R.id.category_infos);
		category_evaluation = (LinearLayout) findViewById(R.id.category_evaluation);
		tipsLv.setVisibility(View.GONE);
		category_infos.setVisibility(View.GONE);
		category_evaluation.setVisibility(View.GONE);
		
		category_food_infos = (LinearLayout) findViewById(R.id.category_food_infos);
		category_evaluation_infos = (LinearLayout) findViewById(R.id.category_evaluation_infos);
		category_evaluation = (LinearLayout) findViewById(R.id.category_evaluation);
		rest_evaluation = (LinearLayout) findViewById(R.id.rest_evaluation);
		rest_evaluation_text = (TextView) findViewById(R.id.rest_evaluation_text);
		evaluation_count = (TextView) findViewById(R.id.evaluation_count);
		tips_infos = (TextView) findViewById(R.id.tips_infos);
		fb = FinalBitmap.create(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getString("id");
		categoryFood = (CategoryVideo) bundle.getSerializable("category");
		intitCategoryInstruction(categoryFood);
		requestData();

	}

	private void requestData() {
		FinalHttp finalHttp = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("id", id);
		finalHttp.get(GlobalData.ip + GlobalData.DETAILCATEGORY, params,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						progressBar.setVisibility(View.VISIBLE);
					}

					@Override
					public void onSuccess(Object t) {
						detailCategory = new DetailCategory(t.toString());
						Team team = detailCategory.getTeams();
						List<Service> services = detailCategory.getServices();
						setDataForServices(services);
						if (team != null) {
							tipsLv.setVisibility(View.VISIBLE);
							setCategoryInstruction(team);
							tips_infos.setText(Html.fromHtml(team.notice));
						}
						
						
						requestComment(id);
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						// TODO Auto-generated method stub
						super.onFailure(t, errorNo, strMsg);
						Toast.makeText(DetailVideoCategoryActivity.this,
								"无法连接网络", Toast.LENGTH_SHORT).show();
						progressBar.setVisibility(View.GONE);
						requestComment(id);
					}
				});
	}

	/**
	 * 加载团购说明信息
	 */
	private void intitCategoryInstruction(CategoryVideo food) {
		TextView teamPrice = (TextView) category_instruction
				.findViewById(R.id.teamPrice);
		TextView marketPrice = (TextView) category_instruction
				.findViewById(R.id.marketPrice);
		TextView qianggouBtn = (TextView) category_instruction
				.findViewById(R.id.qianggouBtn);
		TextView category_title = (TextView) category_instruction
				.findViewById(R.id.category_title);
		TextView category_content = (TextView) category_instruction
				.findViewById(R.id.category_content);
		TextView scale_count = (TextView) category_instruction
				.findViewById(R.id.scale_count);
		RatingBar scoreRatingBar = (RatingBar) category_instruction
				.findViewById(R.id.scoreRatingBar);
		TextView score = (TextView) category_instruction
				.findViewById(R.id.score);
		TextView stoptime = (TextView) category_instruction
				.findViewById(R.id.stoptime);

		teamPrice.setText(food.team_price);
		marketPrice.setText(food.market_price);
		marketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		category_title.setText(food.title);
		category_content.setText(food.product);
		scale_count.setText("已售" + food.now_number);
		score.setText(food.score + "分");
		try {
			scoreRatingBar.setRating(Float.parseFloat(food.score));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			scale_count.setVisibility(View.INVISIBLE);
			score.setVisibility(View.INVISIBLE);
		}
	}

	private void requsetBusinessInfo(String parent_ID) {
		FinalHttp http = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("id", parent_ID);
		http.get(GlobalData.ip + GlobalData.BUSINESS_INFO, params,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onSuccess(Object t) {
						// TODO Auto-generated method stub
						Log.i("FoodCategory", t.toString());

					}
				});
	}

	/**
	 * 设置团购说明
	 * 
	 * @param team
	 */
	// http://tuan.meizhou.com/static/team/2014/0701/14042077957636.jpg
	private void setCategoryInstruction(final Team team) {
		final ImageView category_img = (ImageView) findViewById(R.id.category_img);
		TextView stoptime = (TextView) category_instruction
				.findViewById(R.id.stoptime);
		fb.display(category_img, team.image, ScreenUtil.getWidth(this), 400);
		AsyncTask<String, Integer, Bitmap> task = new AsyncTask<String, Integer, Bitmap>() {
			@Override
			protected Bitmap doInBackground(String... params) {
				try {
					URL url = new URL(params[0]);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setDoInput(true);
					conn.connect();
					InputStream inputStream = conn.getInputStream();
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					return bitmap;
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(Bitmap result) {
				if (result == null) {
					Toast.makeText(getApplicationContext(), "xiazaishibai",
							Toast.LENGTH_LONG).show();
				} else {
					result = ImageUtil.cutBit(result, ScreenUtil
							.getWidth(DetailVideoCategoryActivity.this), 700);
					// result = ImageUtil.zoomBit(result, 720, 200);
					category_img.setImageBitmap(result);
				}
			};
		}.execute(team.image);
		stoptime.setText(team.end_time);
	}

	/**
	 * 设置每个团购具体信息
	 */
	private void setDataForServices(List<Service> services) {
		if (services == null || services.size() == 0) {
			category_infos.setVisibility(View.GONE);
			
			return;
		}
		category_infos.setVisibility(View.VISIBLE);
		for (int i = 0; i < services.size(); i++) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.category_food_infos_item, null);
			TextView typename = (TextView) view.findViewById(R.id.typename);
			TextView name = (TextView) view.findViewById(R.id.name);
			TextView content = (TextView) view.findViewById(R.id.content);
			TextView price = (TextView) view.findViewById(R.id.price);

			typename.setText(services.get(i).typename);
			name.setText(services.get(i).servicename);
			content.setText(services.get(i).servicecontent);
			price.setText(services.get(i).price);
			category_food_infos.addView(view);
		}

	}

	public void requestComment(String teamId) {
		FinalHttp http = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("teamid", id);
		// params.put("id", commentId + "");
		http.get(GlobalData.ip + GlobalData.COMMENT, params,
				new AjaxCallBack<Object>() {
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onSuccess(Object t) {
						// TODO Auto-generated method stub
						progressBar.setVisibility(View.GONE);
						Comments comments = new Comments(t.toString());
						createComment(comments);
					}

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						// TODO Auto-generated method stub
						super.onFailure(t, errorNo, strMsg);
						Toast.makeText(DetailVideoCategoryActivity.this,
								"无法连接网络", Toast.LENGTH_SHORT).show();
						progressBar.setVisibility(View.GONE);
					}
				});
	}

	/**
	 * 制作评价
	 */
	private void createComment(Comments comments) {

		if (comments.list == null || comments.list.size() == 0) {
			category_evaluation.setVisibility(View.GONE);
			evaluation_count.setVisibility(View.GONE);
			return;
		}
		category_evaluation.setVisibility(View.VISIBLE);
		evaluation_count.setVisibility(View.VISIBLE);
		evaluation_count.setText(comments.list.size() + "人评价");
		List<Comment> list = comments.list;
		int count = list.size();
		View categoryInfo = null;
		// 团购数量为1-2个
		if (count == 1 || count == 2) {
			rest_evaluation.setVisibility(View.GONE);
			for (int i = 0; i < count; i++) {
				categoryInfo = LayoutInflater.from(this).inflate(
						R.layout.comment_item, null);
				setdataForItem(categoryInfo, list.get(i));
				category_evaluation_infos.addView(categoryInfo);
			}
		} else if (count > 2) {
			int restCount = count - 2;
			count = 2;
			rest_evaluation.setVisibility(View.VISIBLE);
			rest_evaluation_text.setText("查看本店其他" + restCount + "个团购");
			rest_evaluation.setTag(list);
			rest_evaluation.setOnClickListener(this);
		}

		for (int i = 0; i < count; i++) {
			categoryInfo = LayoutInflater.from(this).inflate(
					R.layout.comment_item, null);
			setdataForItem(categoryInfo, list.get(i));
			category_evaluation_infos.addView(categoryInfo);

		}

	}

	/**
	 * 给评论item填充值
	 * 
	 * @param categoryInfo
	 * @param comment
	 */
	private void setdataForItem(View categoryInfo, Comment comment) {
		TextView name = (TextView) categoryInfo.findViewById(R.id.username);
		TextView time = (TextView) categoryInfo.findViewById(R.id.time);
		RatingBar bar = (RatingBar) categoryInfo.findViewById(R.id.ratingBar);
		TextView commentView = (TextView) categoryInfo
				.findViewById(R.id.textView1);
		LinearLayout imgs = (LinearLayout) categoryInfo.findViewById(R.id.imgs);

		name.setText(comment.username);
		time.setText(comment.replaytime);
		bar.setRating(Float.parseFloat(comment.vote));
		commentView.setText(comment.content);
		if (comment.image == null || "".equals(comment.image)) {
			imgs.setVisibility(View.GONE);
			return;
		}
		String images = comment.image;
		for (int i = 0; i < 1; i++) {
			ImageView image = new ImageView(this);
			LayoutParams params = new LayoutParams(
					(int) (60 * ScreenUtil.getDensity(this)),
					(int) (60 * ScreenUtil.getDensity(this)));
			image.setScaleType(ScaleType.FIT_XY);
			imgs.addView(image, params);
			fb.display(image, images, params.width, params.height);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rest_evaluation:
			List<Comment> list = (List<Comment>) v.getTag();
			for (int i = 2; i < list.size(); i++) {
				View categoryInfo = LayoutInflater.from(this).inflate(
						R.layout.comment_item, null);
				setdataForItem(categoryInfo, list.get(i));
				category_evaluation_infos.addView(categoryInfo);
			}
			rest_evaluation.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}
}
