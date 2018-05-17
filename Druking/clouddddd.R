# install.packages("KoNLP")
# install.packages("wordcloud")
# install.packages("rvest")
# install.packages("RColorBrewer")
# install.packages("httr")
# install.packages("stringr)
# install.packages("RSelenium)
library(wordcloud)
library(RColorBrewer)
library(KoNLP)
library(RSelenium)
library(rvest)
library(httr)
library(stringr)


URL <- readLines("URL.txt")
dir <- readLines("dir.txt")
setwd(dir)

remDr <- remoteDriver(remoteServerAddr="localhost", port=4445L, browserName="chrome")
remDr$open()
remDr$navigate(URL)

Sys.sleep(2)

btn <- remDr$findElement("class", "simplecmt_link_text")
if(is.null(btn)){
  btn <- remDr$findElement("class", "u_cbox_btn_view_comment")
}

btn$clickElement()

Sys.sleep(2)

btn <- remDr$findElement("class", "u_cbox_btn_more")

tryCatch(while(T){btn$clickElement()}, error=function(e) print("done"))


all_review=c()
source <- remDr$getPageSource()[[1]]
main <- read_html(source)
mainfo = html_nodes(main, ".u_cbox_contents")

remDr$close()

useSejongDic()

#명사단어만남기기
review <- html_text(mainfo)
all_review = c(all_review, review)
all_review2 = gsub("[ㄱ-ㅎ]", " ", all_review)
all_review3 = gsub("\\.", " ", all_review2)
all_review4 = gsub(",", " ", all_review3)
all_review5 = gsub("[A-Za-z]", "", all_review4)
all_review6 = gsub("[^[:alpha:]]", " ", all_review5)
write(all_review6, "review1.txt")

data2 <- readLines("review1.txt")

data2 <- sapply(all_review6, extractNoun, USE.NAMES = F)
data3 <- unlist(data2)
data3 <- Filter(function(x){nchar(x)>=2 && nchar(x) <=6}, data3)




#빈도수 계산
nounCount1 <- table(data3)
nounCount <- head(sort(nounCount1, decreasing = TRUE), 70)
palete <- brewer.pal(8,"Set2")

#그림 저장
jpeg(filename = "cloud.jpg", width = 600, height = 600)
wordcloud(names(nounCount), freq = nounCount, scale = c(20,2), rot.per =0.25, min.freq = 3, random.order = FALSE, random.color=TRUE, colors=palete)
dev.off()


#드루킹잡기
dup <- which(duplicated(all_review4))


sink("druking.txt")
print(unique(all_review4[dup]))
sink()

URL <- "URL.txt"
file.remove(URL)


