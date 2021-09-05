# トラブルシューティング

## フォーク・クローンしたリポジトリを最新の状態に更新する

Railway ではテストの判定ロジックに不備があったり、Android側のアップデートがあったりした場合にコードを更新する場合があります。
フォーク・クローンしたリポジトリは、元のリポジトリに更新があったとしても自動的に反映してくれませんので自分で更新をする必要があります。

以下のコマンドを1行ずつ実行してください。

```shell
git stash
git pull https://github.com/TechBowl-japan/android-stations main --no-verify
git stash pop
yarn install
```

正常に実行されているかどうかは以下のコマンドで確認できます。

```shell
git log --pretty=format:"%s" -1
```

以下のような1文が表示されていれば正常に実行されています。

```shell
Merge branch 'main' of github.com:TechBowl-japan/android-stations into main
```

## GitHubアカウントでTechTrainへサインアップしたのでパスワードを設定していない場合

以下のページより、登録したメールアドレスを入力してパスワードリセットを行ってください。

https://techbowl.co.jp/techtrain/resetpassword

メールアドレスがわからない場合は、ログイン後にユーザー情報の編集画面で確認してください。
ログインしていれば、次のURLから確認できます。

https://techbowl.co.jp/techtrain/mypage/profile

## Android Studio のインストール時に HAXM のインストールに失敗する場合

Windows かつエミュレーターを使用する方が対象になります。(Mac を使う方、実機を使用してアプリの動作を確認する方は HAXM のインストールに失敗していても問題ありません)

Android Studio のセットアップ中に以下のようなエラーが表示された場合は Windows 側で仮想化支援機能を有効にする必要があります。

```
This computer does not support Intel Virtualization Technology (VT-x) or it is being exclusively used by Hyper-V. HAXM cannot be installed.
Please ensure Hyper-V is disabled in Windows Features, or refer to the Intel HAXM documentation for more information.
```

以下の記事を参考に、Hyper-V を有効化してください。

https://docs.microsoft.com/ja-jp/virtualization/hyper-v-on-windows/quick-start/enable-hyper-v
