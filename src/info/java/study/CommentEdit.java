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


public class CommentEdit extends HttpServlet {


	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// DB関連の初期設定
		Connection conn = null;
		PreparedStatement comment_insert_state = null;
		PreparedStatement Contributor_name_state = null;
		PreparedStatement thread_read_state = null;
		PreparedStatement comment_read_state = null;
		PreparedStatement thread_info_state = null;
		ResultSet thread_result_set = null;
		ResultSet Contributor_name_result_set =null;
		ResultSet comment_result_set = null;
		ResultSet thread_info_result_set = null;

		// 文字コードの設定
		request.setCharacterEncoding("Windows-31J");

		//thread2からコメントを投稿するスレッド情報を取得
		String thread_id = request.getParameter("thread_id");
		//thread2からコメントを投稿する方のUserIDを取得
		String UserId = request.getParameter("UserId");
		//thread2から投稿内容を取得
		String comment = request.getParameter("comment");

		try {
			// JDBC Driver の登録
			Class.forName("com.mysql.jdbc.Driver");
			// Connectionの作成
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
					"root", "searchman");

//投稿者の名前を取得-------------------------------------------------------------------------------------------
			// sql文作成
			String sql_Contributor_name = "select * from users_list where id =" + UserId + ";";
			System.out.println(sql_Contributor_name);

			//sql実行→実行結果を代入
			Contributor_name_state = conn.prepareStatement(new String(sql_Contributor_name));
			Contributor_name_state.execute();
			Contributor_name_result_set = Contributor_name_state.executeQuery();

			//実行結果を取り出してContributor_nameクラスに代入

			String Contributor_name = null;

			while(Contributor_name_result_set.next()) {
				Contributor_name = Contributor_name_result_set.getString(2);
			}
////スレッドの投稿数を取得-------------------------------------------------------------------------------------
///*			//スレッドの投稿数を取得
//			String sql_thread_count = "select count(*) from comment_list where thread_id =";
//				sql_thread_count = sql_thread_count + thread_id + ";";
//
//			// sql文を表示
//			System.out.println(sql_thread_count);
//
//			// sql文実行準備
//			thread_count_state = conn.prepareStatement(new String(sql_thread_count));
//
//			// sql文実行
//			thread_count_state.execute();
//
//			// 実行結果を、ResultSetクラスに代入
//			thread_count_result_set =thread_count_state.executeQuery();
//
//			//実行結果を取り出してint型の変数に代入する
//			int comment_no_result = thread_count_state.getInt(thread_id);
//
//			System.out.println(comment_no_result);
//			*/
//comment_noが必要であるため投稿数の取得---------------------------------------------------------------------
			//sql文作成の準備 (コメントの読み出し)
			String sql_comment_read = "select * from comment_list where thread_id =" + thread_id + ";";

			// sql文を表示 (コメントの読み出し)
			System.out.println(sql_comment_read);

			// sql文実行準備 (コメントの読み出し)
			comment_read_state = conn.prepareStatement(new String(sql_comment_read));

			// sql文実行 (コメントの読み出し)
			comment_read_state.execute();

			// 実行結果を、ResultSetクラスに代入 (コメントの読み出し)
			comment_result_set = comment_read_state.executeQuery();

			//現在のコメント計測する変数の初期化
			int comment_no_result = 0;

			//繰り返し処理によって現在の投稿数の取得
			while(comment_result_set.next()) {
				comment_no_result++;
			}

			//割り当てるcomment_noを設定(comment_no=現在の投稿数+1)
			comment_no_result++;

			//comment_noを文字列型に変更
			String comment_no_set = Integer.toString(comment_no_result);

//スレッドへの投稿処理---------------------------------------------------------------------------------

			// sql文 の作成（comment_noから）
			String sql_comment_insert = "insert into comment_list(thread_id,comment_no,name,comment,time)";
			sql_comment_insert = sql_comment_insert + "values"+ "(\"" + thread_id + "\"" + ',' + "\"" + comment_no_set + "\"" + ',' + "\"" + Contributor_name + "\"" + ',' + "\"" + comment + "\",now());";

			// sql文を表示
			System.out.println(sql_comment_insert);

			// sql文実行準備
			comment_insert_state = conn.prepareStatement(new String(sql_comment_insert));

			// sql文実行
			comment_insert_state.execute();

//スレッドの一覧を表示------------------------------------------------------------------------
			String sql_thread_read = "select * from thread_list;";

				// sql文を表示
				System.out.println(sql_thread_read);


				thread_read_state = conn.prepareStatement(new String(sql_thread_read));

				// sql文実行
				thread_read_state.execute();

				// 実行結果を、ResultSetクラスに代入
				thread_result_set = thread_read_state.executeQuery();


				// 遷移ページへ、引渡し（Attributeで追加する）
				request.setAttribute("thread_kekka", thread_result_set);


//対応スレッド情報の表示--------------------------------------------------------------------------------------
			// sql文作成の準備 (コメントの読み出し)
			sql_comment_read = "select * from comment_list where thread_id =" + thread_id + ";";

			// sql文を表示 (コメントの読み出し)
			System.out.println(sql_comment_read);

			// sql文実行準備 (コメントの読み出し)
			comment_read_state = conn.prepareStatement(new String(sql_comment_read));

			// sql文実行 (コメントの読み出し)
			comment_read_state.execute();

			// 実行結果を、ResultSetクラスに代入 (コメントの読み出し)
			comment_result_set = comment_read_state.executeQuery();

			// 遷移ページへ、引渡し（Attributeで追加する） (コメントの読み出し)
			request.setAttribute("comment_kekka", comment_result_set);
//書き込み可能なスレッド情報取得---------------------------------------------------------------
			//sql文作成の準備
			String sql_thread_info = "select * from thread_list where thread_id =" + thread_id + ";";

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
//--------------------------------------------------------------------------------------
			// search.jspへ遷移
			request.getRequestDispatcher("/thread2.jsp").forward(request, response);

			// 使用したオブジェクトを終了させる
			conn.close();
			comment_insert_state.close();
			thread_read_state.close();
			comment_read_state.close();
			thread_info_state.close();
			thread_result_set.close();
			comment_result_set.close();
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