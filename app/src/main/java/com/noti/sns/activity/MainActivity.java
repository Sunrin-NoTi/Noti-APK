package com.noti.sns.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.noti.sns.R;
import com.noti.sns.schoolparsing.School;
import com.noti.sns.schoolparsing.SchoolException;
import com.noti.sns.schoolparsing.SchoolSchedule;
import com.noti.sns.utility.BtnPress;
import com.noti.sns.utility.Listsave;
import com.noti.sns.utility.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	//세어드 프리퍼런스 전역변수로 설정
	public static SharedPreferences pref;
	public static SharedPreferences.Editor edit;
	public static School api;
	public static Util u;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		//기본 xml로딩
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);

		final Boolean[] check_down = {false, false};//초기 다운로드 밑 급식 1월 다운로드 체크
		final Boolean[] check_downfail = {false, false};//초기 다운로드 실패 확인
		String school_code = "B100000658";//선린으로 기본값

		//세어드 프리퍼런스 초기화
		pref = this.getSharedPreferences("save", 0);
		edit = pref.edit();

		api = new School(School.Type.HIGH, School.Region.SEOUL, school_code);//학교 객체 생성
		Date today = new Date();//지금 시간 받기


		Intent intent_login = new Intent(this, MenuActivity.class);//메뉴 액티비티 인텐트
		Intent intent_register = new Intent(this, RegisterActivity.class);//회원가입 액티비티 인텐트

		ImageView login_btn = findViewById(R.id.login_btn);//로그인 버튼 객체
		ImageView register_btn = findViewById(R.id.register_btn);//회원가입 버튼 객체
		ImageView passwd_btn = findViewById(R.id.passwd_btn);//비밀번호 찾기 버튼 객체

		ArrayList<List<SchoolSchedule>> schedule_ForCalender = new ArrayList();//학사일정 초기 다운로드를 위한 리스트

		//버전에 따른 전체화면 지원
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window w = getWindow();
			w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

		//그달 급식이 다운로드 되어있는가?
		if (pref.getInt("mealMonth", 0) != today.getMonth() + 1) {
			Toast.makeText(MainActivity.this, "급식을 다운로드 받습니다(매달 1일).", Toast.LENGTH_SHORT).show();//다운로드를 토스트로 알림
			new Thread() {
				@Override
				public void run() {
					try {
						Listsave.SaveSchool.put_meal_month(api.getMonthlyMenu(today.getYear() + 1900, today.getMonth() + 1), today.getMonth() + 1);//교육청 파싱하여 급식 불러옴
						edit.putInt("mealMonth", today.getMonth() + 1);
						edit.apply();
						//다운로드 확인
						check_down[0] = true;
					} catch (SchoolException e) {
						//다운로드 실패
						check_downfail[0] = true;
						e.printStackTrace();
					}
				}
			}.start();
		} else
			//다운로드 되있음
			check_down[0] = true;

		//처음 앱을 켰는가?
		if (pref.getBoolean("first", true)) {
			//처음 킨것임
			Toast.makeText(this, "초기 다운로드를 진행하겠습니다.", Toast.LENGTH_SHORT).show();//다운로드를 토스트로 알림

			//파싱을 활용한 다운로드
			new Thread() {
				@Override
				public void run() {
						if (u.getSchedule(today, edit, schedule_ForCalender)) {
							check_down[1] = true;
							//초기 다운로드 완료
							edit.putBoolean("first", false);
							edit.commit();
						}
						else
							check_downfail[1] = true;
				}
			}.start();
			} else{
				//전에 킨적이 있음
				check_down[1] = true;
			}

			//메뉴 인텐트 실행
			login_btn.setOnClickListener(view -> {
				if (check_downfail[0] || check_downfail[1])
					//다운로드에 오류가 있는 경우
					Toast.makeText(this, "다운로드를 실패했습니다. 앱을 재시작해주세요", Toast.LENGTH_SHORT).show();
				else if (check_down[0] && check_down[1]) {
					//다운로드가 모두 완료된 경우 or 미리 받아져있는 경우
					startActivity(intent_login);
					finish();
				} else
					//아직 다운로드를 받지 않은 경우
					Toast.makeText(this, "초기 다운로드 중입니다!", Toast.LENGTH_SHORT).show();
			});
			View.OnTouchListener btnPress =(view, motionEvent) -> BtnPress.bigBTN(motionEvent, login_btn); //버튼 눌리는 처리
			login_btn.setOnTouchListener(btnPress);

			//회원가입 인텐트 실행
			register_btn.setOnClickListener(view -> startActivity(intent_register));
			register_btn.setOnTouchListener(btnPress);

			//비밀번호 찾기 인텐트 실행
			passwd_btn.setOnClickListener(view -> Log.e("미구현", "ㅇㄹㅇ"));
			passwd_btn.setOnTouchListener(btnPress);
		}
	}
