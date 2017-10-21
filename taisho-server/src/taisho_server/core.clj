(ns taisho-server.core
  (:require [org.httpkit.client :as http]
            [cheshire.core :as json]))

(def trello-access-params (read-string (slurp "credentials.edn")))

(defn json-get [endpoint]
  (-> @(http/get (str "https://api.trello.com/1" endpoint)
                 {:query-params trello-access-params})
      :body
      (json/parse-string true)))

(comment

  (def members (json-get "/boards/wF8wnpha/members"))

  (def board (->> (json-get "/boards/wF8wnpha/lists")
                 (map (fn [{:keys [name id]
                            :as   tlist}]
                        (assoc tlist :cards (json-get (str "/lists/" id "/cards")))))))


  )
