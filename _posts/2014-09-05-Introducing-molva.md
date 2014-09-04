---
layout: post
title: Introducing molva.spb.ru 
---

## How does one gets to know what's going on?

### A word on how do we interact to each other

Imagine a day without any word heard from other people.
Quiet impossible and creepy, isn't it?

We talk to each other, we write emails, do texting by phone, 
send messages, write blog posts, read blog posts, and comment them.

Every bit of information coming through our brain produces some reaction.
Most of times it is *ignorance*, but from time to time we *respond*.

There begins association between words heard and words replied.
These words are chained together, they form a new specie &mdash; **a conversation**.

### Ah, Twitter again

Yes, we do talk a lot at Twitter. It allows not only share your current thinking,
but also comment on someone else's current thinking. Twitter is tremendous source
of public conversations.

There question arises:

> # How can we summarize all the talking over social network and present the most valuable topics in user-friendly form?

Here at [molva.spb.ru](http://molva.spb.ru/), we are trying to answer this question for Russian segment of Twitter network.

### Why Twitter?

Twitter is rather popular, has nice API's, and is **simple**.

### Why Russian segment?

Whilst there exist tons of Twitter trend monitors for English, for Russian they are few and poor.
And Russian is both tricky and beatiful!


## What's the model

The big picture of [molva.spb.ru](http://molva.spb.ru/) is:
 - reads [Twitter streaming API](https://dev.twitter.com/docs/api) 24/7
 - greps only Russian posts
 - finds replys and create conversation chains (post and reply pairs, actually)
 - parses tweets with tremendous [Yandex Tomita Parser](http://api.yandex.ru/tomita/) and stores each tweet's stems
 - selects top **N** most popular stems 
 - builds post-reply frequence distribution (which words are used to answer on which) and weights it, so most common reply-words get lowered and more specific ones get praised
 - runs each-to-each *cosine similarity* for word profiles
 - clusters word profiles into **M** clusters with *k-means algorithm*
 - rank several trendy clusters and display them

The trend analysis is based on *"moving average"*:
 - three time frames are taken: now, now - 3h, now - 6h
 - frame size is 24h
 - trend is fraction of actual word popularity versus expected

## Evaluation

Information presented by [molva.spb.ru](http://molva.spb.ru/) appears to have following broad topics:
 - news 
 - weather
 - time of day
 - leasures
 - general synonymy

The most appealing is ability to observe the *social mentality*, i.e. what does the group of people think about critical news, persons and so on.
The evaluation therefore is to find out how well is current molva.spb.ru main page correlated to top of news, available for example, at [Yandex News](http://news.yandex.ru/).
Can human pick one words group displayed at [molva.spb.ru](http://molva.spb.ru/) and find corresponding news article?

While evaluation is still in progress, and we don't have good numbers, graphs and tables, it's current results seem promising.



