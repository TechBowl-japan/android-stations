# TechTrain Android Railway について

Railway では、 Git で自分が取り組んだ内容を記録(コミット)するときに、挑戦中の Station をクリアしているかどうかを自動でテストします。
この際、挑戦中の Station の内容に即した実装になっているかを最低限のラインとして確認します。
テストが通れば Station クリアとなります。 クリア後、TechTrain の画面に戻り、クリアになっているかを確認してみてください。

## 初期設定
****
### 必要なソフトウェア

|  ツール名   |              バージョン              |
|:-------:|:-------------------------------:|
|  Java   |             11.*以降              |
| Node.js | v20.0.0以降（20.15.0 (LTS)を推奨します。） |
|  Yarn   |              1.*系               |

上記 3 つをインストールする必要があります。

### ソフトウェアのインストール

Mac におけるソフトウェアのインストールのやり方は [こちら](./SETUP_MAC.md) を参考にしてください。

Windows におけるソフトウェアのインストールのやり方は [こちら](./SETUP_WINDOWS.md) を参考にしてください。

### リポジトリのフォークとクローン

#### `android-stations`リポジトリのフォーク

https://github.com/TechBowl-japan/android-stations

上記のページにアクセスし、右上の `Fork` より自分のアカウントへ `android-stations` リポジトリをクローンします。
フォークが完了すると、自動的に `https://github.com/{自分のユーザー名}/android-stations` へ遷移します。

#### `android-stations`リポジトリのクローン

PC上へフォークしたリポジトリをクローンします。
Terminal.app(Mac)/PowerShell(Windows) を開き、クローンするフォルダへ移動したら以下のコマンドを実行します。

```shell
git clone https://github.com/{ユーザー名}/android-stations.git
```

### yarn によるセットアップ

クローンしたばかりのリポジトリは必要なファイルがダウンロードされていないの状態なので、それらをダウンロードする必要があります。
10 分程度掛かることもあるため、気長に待ちましょう。上から順番に**1行ずつ**コマンドを実行してください。

```shell
cd android-stations
yarn install
```

最後のコマンドを実行中に TechTrain へのログインを求められます。画面の指示に従い入力を進めてください。

## トラブルシューティング

何かしら問題が起きた場合はまず [こちら](./TROUBLESHOOTING.md) を確認してください。
