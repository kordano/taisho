(ns taisho-client.core
  (:require [goog.dom :as gdom]
            [kioo.om :refer [content set-attr do-> substitute listen]]
            [kioo.core :refer [handle-wrapper]]
            [om.next :as om :refer-macros [defui]]
            [om.dom :as dom])
    (:require-macros [kioo.om :refer [defsnippet deftemplate]]))

(enable-console-print!)

(defonce app-state (atom {:status-list [{:title "open tasks" :counter 42}
                                        {:title "weekly tasks" :counter 23}
                                        {:title "closed tasks" :counter 666}]}))

(defsnippet status-card "templates/trello_lists.html" [:.status-card]
  [{:keys [counter title]}]
  {[:#counter] (content counter)
   [:#title] (content title)})

(deftemplate status-page "templates/trello_lists.html"
  [{:keys [status-list]}]
  {[:.status-title] (content "Projekt Status")
   [:#status-row] (content (mapv status-card status-list))})

(defui CorePage
  Object
  (render [this]
    (dom/div
     nil
     (status-page (om/props this)))))

(def reconciler
  (om/reconciler
   {:state app-state}))

(om/add-root! reconciler CorePage (gdom/getElement "app"))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

(comment

  (reset! app-state {:status-list [{:title "open tasks" :counter 42}
                                   {:title "weekly tasks" :counter 23}
                                   {:title "closed tasks" :counter 666}]})

  )
