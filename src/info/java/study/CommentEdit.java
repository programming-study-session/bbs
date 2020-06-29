package info.java.study;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

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
		ResultSet thread_result_set = null;
		ResultSet user_info_result_set =null;
		ResultSet comment_result_set = null;

		// 文字コードの設定
		request.setCharacterEncoding("Windows-31J");
		//thread2からコメントを投稿するスレッド情報を取得
		String thread_id = request.getParameter("thread_id");
		//thread2からコメントを投稿する方のUserIDを取得
		String user_id = request.getParameter("UserId");
		//thread2から投稿内容を取得
		String comment = request.getParameter("comment");
		String Enter_Char = request.getParameter("comment");

		//入力された文字列が空白でないかの判定
		String Enter_info = Enter_char_judge.Enter_char_replace(Enter_Char);
		System.out.println("Enter_info = '" + Enter_info + "'");

		if (!Enter_info.equals("")) {

			//user_idを遷移ページへ、引渡し（Attributeで追加する）
			request.setAttribute("user_id_set", user_id);

			try {
				// JDBC Driver の登録
				Class.forName("com.mysql.jdbc.Driver");
				// Connectionの作成
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_study?serverTimezone=UTC&useSSL=false",
						"root", "searchman");

				//投稿者の名前を取得
				user_info_result_set = readUsersDb(conn, user_id);
				user_info_result_set.next();
				String Contributor_name = user_info_result_set.getString(2);

				//投稿対象のスレッドのコメント数を取得
				comment_result_set =countCommentDb(conn ,thread_id);
				comment_result_set.next();
				int comment_no_result = comment_result_set.getInt(1);
				//テーブルcommnet_listにinsertするcommnet_noにする
				comment_no_result++;
				String comment_no_set = Integer.toString(comment_no_result);

				//スレッドへの投稿処理
				editComment(conn,thread_id , comment_no_set , Contributor_name , comment);

				//スレッドの一覧を表示
				thread_result_set = readThreadFromDb(conn);
				request.setAttribute("thread_kekka", thread_result_set);


				//対応スレッド情報の表示
				comment_result_set = readCommentFromDb(conn,thread_id);
				request.setAttribute("comment_kekka", comment_result_set);

				//書き込み可能なスレッドタイトル情報取得
				String currentThreadId = request.getParameter("thread_id");
				HashMap<String,String> threadTitleMap = new HashMap<String,String>();

				String threadId;
				String threadTitle;

				while(thread_result_set.next()){
				  threadId = thread_result_set.getString(1);
				  threadTitle = thread_result_set.getString(2);

				  threadTitleMap.put(threadId, threadTitle);
				}

				thread_result_set.beforeFirst();

				String thread_title_result = threadTitleMap.get(currentThreadId);
				request.setAttribute("thread_title", thread_title_result);

				// search.jspへ遷移
				request.getRequestDispatcher("/thread2.jsp").forward(request, response);

				// 使用したオブジェクトを終了させる
				conn.close();
				//thread_read_state.close();
				//comment_read_state.close();
				thread_result_set.close();
				comment_result_set.close();

			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("http://localhost:8090/SkillShare/Eroor.jsp");

			} finally {
				try {
					// 念のため、finallyでDBとの接続を切断しておく
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				  }
			}
		}

		else {
			response.sendRedirect("http://localhost:8090/SkillShare/Not_Enter_Warning.jsp");
		}

	}

	private ResultSet readUsersDb(Connection conn, String user_id) throws SQLException {
		//ログインに必要なユーザー情報をusers_listから読み出し
		String sql_users_info = "select * from users_list where id =" + user_id + ";";
		System.out.println(sql_users_info);
		PreparedStatement user_info_state =conn.prepareStatement(new String(sql_users_info));
		user_info_state.execute();
		return user_info_state.executeQuery();
	}

	private ResultSet countCommentDb(Connection conn, String thread_id) throws SQLException {
		//ログインに必要なユーザー情報をusers_listから読み出し
		String sql_comment_count = "select count(*) from comment_list where thread_id =" + thread_id + ";";
		System.out.println(sql_comment_count);
		PreparedStatement comment_count_state = conn.prepareStatement(new String(sql_comment_count));
		comment_count_state.execute();
		return comment_count_state.executeQuery();
	}

	private static void editComment(Connection conn, String thread_id, String comment_no_set, String Contributor_name,String comment) throws SQLException {
		String sql_comment_insert = "insert into comment_list(thread_id,comment_no,name,comment,time)";
		sql_comment_insert = sql_comment_insert + "values"+ "(\"" + thread_id + "\"" + ',' + "\"" + comment_no_set + "\"" + ',' + "\"" + Contributor_name + "\"" + ',' + "\"" + comment + "\",now());";
		System.out.println(sql_comment_insert);
		PreparedStatement comment_insert_state = conn.prepareStatement(new String(sql_comment_insert));
		comment_insert_state.execute();
		comment_insert_state.close();
	}

	private ResultSet readThreadFromDb(Connection conn) throws SQLException {

		// sql文作成の準備 (スレッドの読み出し)
		String sql_thread_read = "select * from thread_list;";

		// (スレッドの読み出し)
		System.out.println(sql_thread_read);

		// sql文実行準備 (スレッドの読み出し)
		PreparedStatement thread_read_state = conn.prepareStatement(new String(sql_thread_read));

		// sql文実行 (スレッドの読み出し)
		thread_read_state.execute();

		// 実行結果を、Resulthread_result_setクラスに代入 (スレッドの読み出し)
		return thread_read_state.executeQuery();

	}

	private ResultSet readCommentFromDb(Connection conn, String thread_id) throws SQLException {
		// sql文作成の準備 (コメントの読み出し)
		String sql_comment_read = "select * from comment_list where thread_id =" + thread_id + ";";

		// sql文を表示 (コメントの読み出し)
		System.out.println(sql_comment_read);

		// sql文実行準備 (コメントの読み出し)
		PreparedStatement comment_read_state = conn.prepareStatement(new String(sql_comment_read));

		// sql文実行 (コメントの読み出し)
		comment_read_state.execute();

		return comment_read_state.executeQuery();
	}


}