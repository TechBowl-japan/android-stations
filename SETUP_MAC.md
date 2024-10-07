# Mac 環境でのソフトウェアのインストール

Homebrew を使って必要なソフトウェアをインストールする方法を紹介します。

## 1. Homebrew のインストール

**パッケージ管理ツール**と呼ばれる、ソフトウェアのインストールを簡単にするためのツールをインストールします。
[Homebrew](https://brew.sh/ja/) を用いた環境構築を推奨します。
Terminal.app を開き、次のコマンドを実行してください。(すでに Homebrew をインストール済みの方は、この手順を飛ばしてください)

```shell
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

## 2. 必要なソフトウェアのインストール

Railway を進めるには、**Git**、**node**、**yarn**, **Java** のインストールが必要です。 Terminal.app を開き、次のコマンドを実行してください。

```shell
brew install node@14 yarn openjdk@11
```
