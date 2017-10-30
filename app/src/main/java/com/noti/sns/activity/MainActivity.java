package com.noti.sns.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.noti.sns.R;
import com.noti.sns.schoolparsing.School;
import com.noti.sns.schoolparsing.SchoolException;
import com.noti.sns.schoolparsing.SchoolSchedule;
import com.noti.sns.server.Connection;
import com.noti.sns.utility.BtnPress;
import com.noti.sns.utility.Listsave;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	//세어드 프리퍼런스 전역변수로 설정
	public static SharedPreferences pref;
	public static SharedPreferences.Editor edit;
	public static School api;
	public static String id;
	public static String pw;
	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		//기본 xml로딩
		setContentView(R.layout.activity_main);
		super.onCreate(savedInstanceState);
		//세어드 프리퍼런스 초기화
		pref = this.getSharedPreferences("save", 0);
		edit = pref.edit();
		Log.e("sd", String.valueOf(pref.getBoolean("save_login",false)));
		if(pref.getBoolean("save_login",false))
			login(pref.getString("save_id",""),pref.getString("save_pw",""));

		final Boolean[] check_down = {false, false};//초기 다운로드 밑 급식 1월 다운로드 체크
		final Boolean[] check_downfail = {false, false};//초기 다운로드 실패 확인
		String school_code = "B100000658";//선린으로 기본값
		/*
			 * 반환 실제 값은 각각 response[1] / response[3]으로 접근할 수 있음
			 * response,failed 학교이름 에러
			 * response,login_success,code,code // code 보내줌
		*/
		api = new School(School.Type.HIGH, School.Region.SEOUL, school_code);//학교 객체 생성
		Date today = new Date();//지금 시간 받기

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
			//다운로드 안되있음
			Toast.makeText(MainActivity.this, "급식을 다운로드 받습니다(매달 1일).", Toast.LENGTH_SHORT).show();//다운로드를 토스트로 알림
			//파싱을 활용한 다운로드
			new Thread() {
				@Override
				public void run() {
					try {
						Listsave.SaveSchool.put_meal_month(api.getMonthlyMenu(today.getYear() + 1900, today.getMonth() + 1), today.getMonth() + 1);//교육청 파싱하여 급식 불러옴
						//다운받아진 달 저장
						edit.putInt("mealMonth", today.getMonth() + 1);
						edit.commit();
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
					try {
						//3월 ~ 내년 2월까지의 학사일정을 받아옴
						for (int i = 1; i <= 12; i++)
							schedule_ForCalender.add(api.getMonthlySchedule(today.getYear() + 1900, i));

						int event_num = 0;//총 일정의 갯수를 구하는 객체
						int feb_Days = 27;//내년 2월의 날짜 수
						int next_year = today.getYear() + 1901;//내년의 년도

						//31일인 달 리스트
						ArrayList<Integer> m_31 = new ArrayList<>();
						m_31.add(3);
						m_31.add(5);
						m_31.add(7);
						m_31.add(8);
						m_31.add(10);
						m_31.add(12);

						//30일인 달 리스트
						ArrayList<Integer> m_30 = new ArrayList<>();
						m_30.add(4);
						m_30.add(6);
						m_30.add(9);
						m_30.add(11);

						//내년이 윤년인가?
						if (((next_year % 4 == 0) && !(next_year % 100 == 0)) || (next_year % 400 == 0))
							feb_Days = 28;

						//받아온 학사일정을 나눠 캘린더 이벤트로 저장
						for (int i = 3; i <= 12; i++) {
							if (m_31.contains(i))
								for (int j = 0; j <= 30; j++) {
									if (!schedule_ForCalender.get(i - 1).get(j).schedule.equals("")) {
										edit.putInt("eventm" + event_num, i);
										edit.putInt("eventd" + event_num, j);
										event_num++;
									}
								}
							if (m_30.contains(i))
								for (int j = 0; j <= 29; j++) {
									if (!schedule_ForCalender.get(i - 1).get(j).schedule.equals("")) {
										edit.putInt("eventm" + event_num, i);
										edit.putInt("eventd" + event_num, j);
										event_num++;
									}
								}
						}
						//내년 1월 처리
						for (int j = 0; j <= 30; j++) {
							if (!schedule_ForCalender.get(0).get(j).schedule.equals("")) {
								edit.putInt("eventm" + event_num, 1);
								edit.putInt("eventd" + event_num, j);
								event_num++;
							}
						}
						//내년 2월 처리
						for (int j = 0; j <= feb_Days; j++) {
							if (!schedule_ForCalender.get(1).get(j).schedule.equals("")) {
								edit.putInt("eventm" + event_num, 2);
								edit.putInt("eventd" + event_num, j);
								event_num++;
							}
						}
						//학사일정 갯수 저장
						edit.putInt("scnum", event_num);
						edit.commit();

						Listsave.SaveSchool.push_Hac(schedule_ForCalender);//학사일정 객체를 저장

						//초기 다운로드 완료
						edit.putBoolean("first", false);
						edit.commit();
						check_down[1] = true;
					} catch (SchoolException e) {
						//초기 다운로드 실패
						check_downfail[1] = true;
						e.printStackTrace();
					}
				}
			}.start();
		} else {
			//전에 킨적이 있음
			check_down[1] = true;
		}
		//메뉴 인텐트 실행
		login_btn.setOnClickListener(view -> {
			/*
				 * 반환값 실제 값은 각각 response[1] / response[3]으로 접근할 수 있음
				 * response,login_failed:nonexistent 계정없음
				 * response,login_failed:password 비밀번호기반 로그인 실패
				 * response,login_failed:token 토큰 로그인 실패 ==> 로그인창 띄어줘야함
				 * response,login_success,code,schoolcode // 토큰은 token 에 저장할 것
			*/
			if (check_downfail[0] || check_downfail[1])

				//다운로드에 오류가 있는 경우
				Toast.makeText(this, "다운로드를 실패했습니다. 앱을 재시작해주세요", Toast.LENGTH_SHORT).show();
			else if (check_down[0] && check_down[1]) {

				//다운로드가 모두 완료된 경우 or 미리 받아져있는 경우
				EditText email_text = findViewById(R.id.email_text);
				EditText pw_text = findViewById(R.id.pw_text);
				Log.e("12","이메일:"+email_text.getText().toString());
				login(email_text.getText().toString(),pw_text.getText().toString());

			} else
				//아직 다운로드를 받지 않은 경우
				Toast.makeText(this, "초기 다운로드 중입니다!", Toast.LENGTH_SHORT).show();
		});

		login_btn.setOnTouchListener((view, motionEvent) -> {
			BtnPress.bigBTN(motionEvent, login_btn);
			return false;
		});//버튼 눌리는 처리

		//회원가입 인텐트 실행
		register_btn.setOnClickListener(view -> {


			startActivity(intent_register);

		});
		register_btn.setOnTouchListener((view, motionEvent) -> BtnPress.smallBTN(motionEvent, register_btn));//버튼 눌리는 처리

		//비밀번호 찾기 인텐트 실행
		passwd_btn.setOnClickListener(view -> Log.e("미구현", "ㅇㄹㅇ"));
		passwd_btn.setOnTouchListener((view, motionEvent) -> BtnPress.smallBTN(motionEvent, passwd_btn));//버튼 눌리는 처리
	}

	@Override
	public void onBackPressed() {
		ActivityCompat.finishAffinity(this);
	}

	public void login(String p0, String p1){
		String[] response;
		EditText email_text = findViewById(R.id.email_text);
		EditText pw_text = findViewById(R.id.pw_text);
		if (p0.equals(""))
			Toast.makeText(this, "이메일 칸이 비어있습니다.", Toast.LENGTH_SHORT).show();
		else if (p1.equals(""))
			Toast.makeText(this, "비밀번호 칸이 비어있습니다.", Toast.LENGTH_SHORT).show();
		else {
			try {
				Intent intent_login = new Intent(this, MenuActivity.class);//메뉴 액티비티 인텐트

				JSONObject jo = new JSONObject();
				jo.put("id", p0);
				jo.put("password", p1);
				response = Connection.sendJSON(getString(R.string.url) + "/login/", jo.toString());
				if (response[1].equals("login_success")) {
					edit.putString("school_code", response[3]);
					edit.putString("save_id", p0);
					edit.putString("save_pw", p1);
					edit.putBoolean("save_login", true);
					edit.commit();
					startActivity(intent_login);
					finish();
				} else if (response[1].equals("login_failed:password")) {
					Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
					pw_text.setText("");
				}
			} catch (Exception e) {
				Log.e("1", String.valueOf(e));
				Log.e("1", String.valueOf(email_text.getText()));

				Toast.makeText(this, "인터넷 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
			}
		}
	}
}