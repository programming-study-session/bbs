package info.java.study;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ThreadEdit extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// DB関連の初期設定
		Connection conn = null;
		PreparedStatement thread_creator_state = null;
		PreparedStatement thread_insert_state = null;
		PreparedStatement thread_read_state = null;
		PreparedStatement thread_info_state = null;
		ResultSet thread_creator_result_set = null;
		ResultSet thread_result_set = null;
		ResultSet thread_info_result_set = null;


		// 文字コードの設定
		request.setCharacterEncoding("Windows-31J");

		//thread2から作成するスレッド名,スレッド作成者のIDを取得
		String topic = request.getParameter("topic");
		String UserId = request.getParameter("UserID");

		try {
			// JDBC Driver の登録
			Class.forName("com.mysql.jdbc.Driver");
			// Connectionの作成
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
					"root", "searchman");
//スレッド作成者の名前をuses_listから読み出し-------------------------------------------------------------------------

			//sql文の作成
            String sql_thread_creator = "select * from users_list where id =" + UserId + ";";
            System.out.println(sql_thread_creator);

            //
            thread_creator_state = conn.prepareStatement(new String(sql_thread_creator));
            thread_creator_state.execute();
            thread_creator_result_set = thread_creator_state.executeQuery();



            String thread_creator = null;

            while(thread_creator_result_set.next()) {
            	thread_creator = thread_creator_result_set.getString(2);
            }
            System.out.println(thread_creator);

//スレッドの作成処理------------------------------------------------------------------------
			// sql文 の作成
			String sql_thread_insert = "insert into thread_list(thread_title,name,time)";
			sql_thread_insert = sql_thread_insert + "values"+ "(\""  + topic + "\",\""+ thread_creator + "\"," + "now()" +");";

			// sql文を表示
			System.out.println(sql_thread_insert);

			// sql文実行準備
			thread_insert_state = conn.prepareStatement(new String(sql_thread_insert));

			// sql文実行
			thread_insert_state.execute();

//スレッドの一覧を表示----------------------------------------------------------------------
			// sql文 の作成
			String sql_thread_read = "select * from thread_list;";

			//sql文を表示
			System.out.println(sql_thread_read);

			thread_read_state = conn.prepareStatement(new String(sql_thread_read));

				// sql文実行
			thread_read_state.execute();

			// 実行結果を、ResultSetクラスに代入
			thread_result_set = thread_read_state.executeQuery();


			// 遷移ページへ、引渡し（Attributeで追加する）
			request.setAttribute("thread_kekka", thread_result_set);

//対応スレッド情報の表示-----------------------------------------------------------------------------------
			//sql文作成の準備
			String sql_thread_info = "select thread_id, thread_title from thread_list where thread_id=(select max(thread_id) from thread_list);";

			// sql文を表示
			System.out.println(sql_thread_info);

			// sql文実行準備
			thread_info_state = conn.prepareStatement(new String(sql_thread_info));

			// sql文実行
			thread_info_state.execute();

			// 実行結果を、ResultSetクラスに代入
			thread_info_result_set = thread_info_state.executeQuery();

			// 遷移ページへ、引渡し（Attributeで追加する）
			request.setAttribute("thread_info_kekka", thread_info_result_set);
//-------------------------------------------------------------------------------------


			// search.jspへ遷移
			request.getRequestDispatcher("/thread2.jsp").forward(request, response);

			// 使用したオブジェクトを終了させる
			conn.close();
			thread_insert_state.close();
			thread_read_state.close();
			thread_info_state.close();
			thread_result_set.close();
			thread_info_result_set.close();

		} catch (Exception e) {
			request.getRequestDispatcher("/Eroor.jsp");

		} finally {
			try {
				// 念のため、finallyでDBとの接続を切断しておく
				conn.close();
			} catch (Exception e) {
			}
		}

	}

}
