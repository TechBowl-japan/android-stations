# Mac 環境でのソフトウェアのインストール

Homebrew を使って必要なソフトウェアをインストールする方法を紹介します。

## 1. Homebrew のインストール

**パッケージ管理ツール**と呼ばれる、ソフトウェアのインストールを簡単にするためのツールをインストールします。
[Homebrew](https://brew.sh/index_ja) を用いた環境構築を推奨します。
Terminal.app を開き、次のコマンドを実行してください。(すでに Homebrew をインストール済みの方は、この手順を飛ばしてください)

```shell
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)
```

## 2. 必要なソフトウェアのインストール

Railway を進めるには、**Git**、**node**、**yarn**, **Java** のインストールが必要です。 Terminal.app を開き、次のコマンドを実行してください。

```shell
brew install node yarn openjdk
```
