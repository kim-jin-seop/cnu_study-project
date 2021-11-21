from selenium import webdriver
from bs4 import BeautifulSoup
import os
import re
import random
import time
import pandas as pd


driver = webdriver.Chrome('./chromedriver')
driver.implicitly_wait(3)

#영화 번호 가져오기
def get_movie_link(soup):
    movie_links = soup.select('a[href]')

    movie_links_list = []
    for link in movie_links:
        if re.search(r'st=mcode&sword' and r'&target=after$', link['href']):
            target_url = 'https://movie.naver.com/movie/point/af/list.nhn' + str(link['href'])
            movie_links_list.append(target_url)

    return movie_links_list[1:]

#해당 reviewNo의 리뷰를 쓴 사람의 모든 리뷰 정보(reviewNo 이하) 가져오기
def get_review(reviewNo):
    user_id = []
    user_rating = []
    user_reviewnum = []
    user_movieId = []
    page = 1
    FirstReviewnum = 0
    while True:
        driver.get('https://movie.naver.com/movie/point/af/list.nhn?st=nickname&sword={0}&target=after&page={1}'.format(reviewNo,page))
        page +=1

        html = driver.page_source
        soup = BeautifulSoup(html, 'html.parser')
        userId = soup.select('table.list_netizen > tbody > tr > td > a.author')
        reviewRating = soup.select('#old_content > table > tbody > tr > td.point')
        reviewNum = soup.select('#old_content > table > tbody > tr > td.ac.num')
        if len(reviewNum) == 0:
            break
        if (page != 2) and (FirstReviewnum == int(str(reviewNum[0]).replace('<td class="ac num">', '').replace('</td>', ''))):
            break
        FirstReviewnum = int(str(reviewNum[0]).replace('<td class="ac num">', '').replace('</td>', ''))

        for i in range(len(reviewRating)):
            user = userId[i].text.replace('*', '')
            rating = str(reviewRating[i]).replace('<td class="point">', '').replace('</td>', '')
            num = str(reviewNum[i]).replace('<td class="ac num">', '').replace('</td>', '')
            num = int(num)
            user_id.append(user)
            user_rating.append(rating)
            user_reviewnum.append(num)
        movie_links = get_movie_link(soup)
        movieId = [link.replace('https://movie.naver.com/movie/point/af/list.nhn?st=mcode&sword=', '').replace('&target=after', '') for link in movie_links]
        user_movieId = user_movieId + movieId
        time.sleep(random.randrange(2, 5))

    userData = pd.DataFrame()
    userData['userId'] = user_id
    userData['rating'] = user_rating
    userData['movieId'] = user_movieId
    userData['reviewNo'] = user_reviewnum
    userData = userData[userData['reviewNo'] <= int(reviewNo)]
    del userData['reviewNo']
    return userData

def main():
    data = pd.read_csv('./data/naver_user.csv')
    df = pd.DataFrame()
    for reviewNum in data['reviewNo']:
        ReviewData = get_review(reviewNum)
        df = pd.concat([df,ReviewData])
    df = df[False == df.duplicated(['userId', 'rating', 'movieId'], keep='first')]
    df.to_csv('./data/rating.csv', index = False)
    driver.close()

if __name__ == "__main__":
    main()
