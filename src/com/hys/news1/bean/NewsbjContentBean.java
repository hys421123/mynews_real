package com.hys.news1.bean;

public class NewsbjContentBean {
	// ����
		private String title;
		// �������ţ�����ͼƬ��
		private ContentTopNewsBean[] ContentTopNews;
		// ������
		private ContentNewsItemBean[] news;
		// ��������
		private String more;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public ContentTopNewsBean[] getTopnews() {
			return ContentTopNews;
		}

		public void setTopnews(ContentTopNewsBean[] ContentTopNews) {
			this.ContentTopNews = ContentTopNews;
		}

		public ContentNewsItemBean[] getNews() {
			return news;
		}

		public void setNews(ContentNewsItemBean[] news) {
			this.news = news;
		}

		public String getMore() {
			return more;
		}

		public void setMore(String more) {
			this.more = more;
		}

}
